package com.tarikyasar.curmin.presentation.screens.currency_watchlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.domain.usecase.api.ConvertCurrencyUseCase
import com.tarikyasar.curmin.domain.usecase.api.GetCurrencySymbolsUseCase
import com.tarikyasar.curmin.domain.usecase.database.DeleteCurrencyWatchlistItemUseCase
import com.tarikyasar.curmin.domain.usecase.database.GetCurrencyWatchlistItemsUseCase
import com.tarikyasar.curmin.domain.usecase.database.InsertCurrencyWatchlistItemUseCase
import com.tarikyasar.curmin.domain.usecase.database.UpdateCurrencyWatchlistItemUseCase
import com.tarikyasar.curmin.utils.DateUtils
import com.tarikyasar.curmin.utils.manager.PreferenceManager
import com.tarikyasar.curmin.utils.receivers.LoadingReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CurrencyWatchlistViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val getCurrencyWatchlistItems: GetCurrencyWatchlistItemsUseCase,
    private val insertCurrencyWatchlistItemUseCase: InsertCurrencyWatchlistItemUseCase,
    private val deleteCurrencyWatchlistItemUseCase: DeleteCurrencyWatchlistItemUseCase,
    private val updateCurrencyWatchlistItemUseCase: UpdateCurrencyWatchlistItemUseCase,
    private val getCurrencySymbolsUseCase: GetCurrencySymbolsUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CurrencyWatchlistState())
    val state: State<CurrencyWatchlistState> = _state

    init {
        getSymbols()
        getCurrencies(refreshList = false)
        getAskToRemoveItemParameter()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrencies(refreshList: Boolean) {
        getCurrencyWatchlistItems().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    LoadingReceiver.sendLoadingEvents(false)
                    if (refreshList) {
                        _state.value.currencies.forEach { currencyWatchlistItemData ->
                            convertCurrency(
                                currencyWatchlistItemData = currencyWatchlistItemData,
                                base = currencyWatchlistItemData.baseCurrencyCode ?: "USD",
                                target = currencyWatchlistItemData.targetCurrencyCode ?: "TRY",
                            )
                        }
                    } else {
                        _state.value =
                            _state.value.copy(
                                currencies = (result.data as MutableList<CurrencyWatchlistItemData>)
                            )
                    }
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred."
                    )
                    LoadingReceiver.sendLoadingEvents(false)
                }
                is Resource.Loading -> {
                    LoadingReceiver.sendLoadingEvents(true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteCurrency(currencyWatchlistItemUid: String) {
        deleteCurrencyWatchlistItemUseCase(currencyWatchlistItemUid).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value.currencies.remove(_state.value.currencies.find {
                        it.uid == currencyWatchlistItemUid
                    })

                    _state.value = _state.value.copy(
                        currencies = _state.value.currencies,
                    )
                    LoadingReceiver.sendLoadingEvents(false)

                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred."
                    )
                    LoadingReceiver.sendLoadingEvents(false)
                }
                is Resource.Loading -> LoadingReceiver.sendLoadingEvents(true)

            }
        }.launchIn(viewModelScope)
    }

    fun createCurrencyWatchlisttem(
        baseCurrencyCode: String,
        targetCurrencyCode: String
    ) {
        var newCurrencyWatchlistItem: CurrencyWatchlistItemData? = null

        convertCurrencyUseCase(
            fromCurrencySymbol = baseCurrencyCode,
            toCurrencySymbol = targetCurrencyCode,
            amount = 1.0
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    newCurrencyWatchlistItem = CurrencyWatchlistItemData(
                        uid = UUID.randomUUID().toString(),
                        baseCurrencyCode = baseCurrencyCode,
                        targetCurrencyCode = targetCurrencyCode,
                        rate = result.data?.rate ?: 0.0,
                        date = DateUtils.formatTime(LocalDateTime.now())
                    )

                    insertCurrency(newCurrencyWatchlistItem!!)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred."
                    )
                    LoadingReceiver.sendLoadingEvents(false)
                }
                is Resource.Loading -> LoadingReceiver.sendLoadingEvents(true)
            }
        }.launchIn(viewModelScope)
    }

    fun getAskToRemoveItemParameter() {
        _state.value = _state.value.copy(
            askToRemoveItemParameter = preferenceManager.getPreference()
        )
    }

    private fun getSymbols() {
        getCurrencySymbolsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        symbols = result.data ?: emptyList()
                    )
                    LoadingReceiver.sendLoadingEvents(false)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred."
                    )
                    LoadingReceiver.sendLoadingEvents(false)
                }
                is Resource.Loading -> LoadingReceiver.sendLoadingEvents(true)

            }
        }.launchIn(viewModelScope)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertCurrency(
        currencyWatchlistItemData: CurrencyWatchlistItemData,
        base: String,
        target: String,
    ) {
        convertCurrencyUseCase(
            fromCurrencySymbol = base,
            toCurrencySymbol = target,
            amount = 1.0
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    updateCurrencyWatchlistItemData(
                        currencyWatchlistItemData.copy(
                            rate = result.data?.rate ?: 0.0,
                            date = DateUtils.formatTime(LocalDateTime.now())
                        )
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred."
                    )
                    LoadingReceiver.sendLoadingEvents(false)
                }
                is Resource.Loading -> LoadingReceiver.sendLoadingEvents(true)
            }
        }.launchIn(viewModelScope)
    }

    private fun insertCurrency(currencyWatchlistItem: CurrencyWatchlistItemData) {
        insertCurrencyWatchlistItemUseCase(currencyWatchlistItem).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        currencies = (_state.value.currencies + currencyWatchlistItem) as MutableList<CurrencyWatchlistItemData>
                    )
                    LoadingReceiver.sendLoadingEvents(false)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred."
                    )
                    LoadingReceiver.sendLoadingEvents(false)
                }
                is Resource.Loading -> {
                    LoadingReceiver.sendLoadingEvents(true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateCurrencyWatchlistItemData(
        currencyWatchlistItem: CurrencyWatchlistItemData
    ) {
        updateCurrencyWatchlistItemUseCase(
            currencyWatchlistItem
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    getCurrencies(false)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred."
                    )
                    LoadingReceiver.sendLoadingEvents(false)
                }
                is Resource.Loading -> LoadingReceiver.sendLoadingEvents(true)
            }
        }.launchIn(viewModelScope)
    }
}