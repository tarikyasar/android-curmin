package com.tarikyasar.curmin.presentation.screens.currency_watchlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.domain.usecase.api.GetCurrencySymbolsUseCase
import com.tarikyasar.curmin.domain.usecase.database.DeleteCurrencyWatchlistItemUseCase
import com.tarikyasar.curmin.domain.usecase.database.GetCurrencyWatchlistItemsUseCase
import com.tarikyasar.curmin.domain.usecase.database.InsertCurrencyWatchlistItemUseCase
import com.tarikyasar.curmin.utils.manager.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CurrencyWatchlistViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val getCurrencyWatchlistItems: GetCurrencyWatchlistItemsUseCase,
    private val insertCurrencyWatchlistItemUseCase: InsertCurrencyWatchlistItemUseCase,
    private val deleteCurrencyWatchlistItemUseCase: DeleteCurrencyWatchlistItemUseCase,
    private val getCurrencySymbolsUseCase: GetCurrencySymbolsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CurrencyWatchlistState())
    val state: State<CurrencyWatchlistState> = _state

    init {
        getSymbols()
        getCurrencies()
        getAskToRemoveItemParameter()
    }

    fun getCurrencies() {
        getCurrencyWatchlistItems().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value =
                        _state.value.copy(
                            currencies = (result.data as MutableList<CurrencyWatchlistItemData>),
                            isLoading = false
                        )
                }
                is Resource.Error -> _state.value = _state.value.copy(
                    error = result.message ?: "An unexpected error occurred.",
                    isLoading = false
                )
                is Resource.Loading -> _state.value = _state.value.copy(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }

    fun insertCurrency(currencyWatchlistItem: CurrencyWatchlistItemData) {
        insertCurrencyWatchlistItemUseCase(currencyWatchlistItem).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        currencies = (_state.value.currencies + currencyWatchlistItem) as MutableList<CurrencyWatchlistItemData>,
                        isLoading = false
                    )
                }
                is Resource.Error -> _state.value = _state.value.copy(
                    error = result.message ?: "An unexpected error occurred.",
                    isLoading = false
                )
                is Resource.Loading -> _state.value = _state.value.copy(isLoading = true)
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
                        error = result.message ?: "An unexpected error occurred.",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
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
                is Resource.Success -> _state.value = _state.value.copy(
                    currencySymbols = result.data ?: emptyList()
                )
                is Resource.Error -> _state.value = _state.value.copy(
                    error = result.message ?: "An unexpected error occurred."
                )
                is Resource.Loading -> _state.value =
                    _state.value.copy(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }

}