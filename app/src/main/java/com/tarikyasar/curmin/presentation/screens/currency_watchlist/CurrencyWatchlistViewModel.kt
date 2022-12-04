package com.tarikyasar.curmin.presentation.screens.currency_watchlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarikyasar.curmin.common.DatesInMs
import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.domain.usecase.api.GetCurrencyFluctuationUseCase
import com.tarikyasar.curmin.domain.usecase.api.GetCurrencySymbolsUseCase
import com.tarikyasar.curmin.domain.usecase.database.DeleteCurrencyWatchlistItemUseCase
import com.tarikyasar.curmin.domain.usecase.database.GetCurrencyWatchlistItemsUseCase
import com.tarikyasar.curmin.domain.usecase.database.InsertCurrencyWatchlistItemUseCase
import com.tarikyasar.curmin.domain.usecase.database.UpdateCurrencyWatchlistItemUseCase
import com.tarikyasar.curmin.utils.DateUtils
import com.tarikyasar.curmin.utils.manager.PreferenceManager
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
    private val getCurrencyFluctuationUseCase: GetCurrencyFluctuationUseCase
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
                    if (refreshList) {
                        _state.value.currencies.forEach { currencyWatchlistItemData ->
                            val todayInMs = System.currentTimeMillis()

                            getCurrencyFluctuation(
                                startDate = DateUtils.formatTime(todayInMs - DatesInMs.DAY.value),
                                endDate = DateUtils.formatTime(todayInMs),
                                baseCurrencyCode = currencyWatchlistItemData.baseCurrencyCode
                                    ?: "USD",
                                targetCurrencyCode = currencyWatchlistItemData.targetCurrencyCode
                                    ?: "TRY",
                                currencyWatchlistItemData = currencyWatchlistItemData
                            )
                        }
                    } else {
                        _state.value =
                            _state.value.copy(
                                currencies = (result.data as MutableList<CurrencyWatchlistItemData>),
                                isLoading = false
                            )
                    }
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
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
                        isLoading = false
                    )

                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun createCurrencyWatchlisttem(
        baseCurrencyCode: String,
        targetCurrencyCode: String
    ) {
        var newCurrencyWatchlistItem: CurrencyWatchlistItemData? = null
        val todayInMs = System.currentTimeMillis()

        getCurrencyFluctuationUseCase(
            startDate = DateUtils.formatTime(todayInMs - DatesInMs.DAY.value),
            endDate = DateUtils.formatTime(todayInMs),
            baseCurrencyCode = baseCurrencyCode,
            targetCurrencyCode = targetCurrencyCode
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    newCurrencyWatchlistItem = CurrencyWatchlistItemData(
                        uid = UUID.randomUUID().toString(),
                        baseCurrencyCode = baseCurrencyCode,
                        targetCurrencyCode = targetCurrencyCode,
                        rate = result.data?.endRate ?: 0.0,
                        date = DateUtils.formatTime(LocalDateTime.now()),
                        change = result.data?.change
                    )

                    insertCurrency(newCurrencyWatchlistItem!!)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getAskToRemoveItemParameter() {
        _state.value = _state.value.copy(
            askToRemoveItemParameter = preferenceManager.getPreference()
        )
    }

    private fun getCurrencyFluctuation(
        startDate: String,
        endDate: String,
        baseCurrencyCode: String,
        targetCurrencyCode: String,
        currencyWatchlistItemData: CurrencyWatchlistItemData,
    ) {
        getCurrencyFluctuationUseCase(
            startDate = startDate,
            endDate = endDate,
            baseCurrencyCode = baseCurrencyCode,
            targetCurrencyCode = targetCurrencyCode
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    updateCurrencyWatchlistItemData(
                        currencyWatchlistItemData.copy(
                            rate = result.data?.endRate ?: 0.0,
                            date = DateUtils.formatTime(LocalDateTime.now()),
                            change = result.data?.change
                        )
                    )
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getSymbols() {
        getCurrencySymbolsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        symbols = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun insertCurrency(currencyWatchlistItem: CurrencyWatchlistItemData) {
        insertCurrencyWatchlistItemUseCase(currencyWatchlistItem).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        currencies = (_state.value.currencies + currencyWatchlistItem) as MutableList<CurrencyWatchlistItemData>,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
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
                        error = result.message,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}