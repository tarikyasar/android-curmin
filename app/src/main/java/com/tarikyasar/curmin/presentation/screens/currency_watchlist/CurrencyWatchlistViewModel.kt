package com.tarikyasar.curmin.presentation.screens.currency_watchlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItem
import com.tarikyasar.curmin.domain.usecase.database.DeleteCurrencyWatchlistItemUseCase
import com.tarikyasar.curmin.domain.usecase.database.GetCurrencyWatchlistItemsUseCase
import com.tarikyasar.curmin.domain.usecase.database.InsertCurrencyWatchlistItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CurrencyWatchlistViewModel @Inject constructor(
    private val getCurrencyWatchlistItems: GetCurrencyWatchlistItemsUseCase,
    private val insertCurrencyWatchlistItemUseCase: InsertCurrencyWatchlistItemUseCase,
    private val deleteCurrencyWatchlistItemUseCase: DeleteCurrencyWatchlistItemUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CurrencyWatchlistState())
    val state: State<CurrencyWatchlistState> = _state

    init {
        getCurrencies()
    }

    fun getCurrencies() {
        getCurrencyWatchlistItems().onEach { result ->
            when (result) {
                is Resource.Success -> _state.value =
                    CurrencyWatchlistState(currencies = result.data ?: emptyList())
                is Resource.Error -> _state.value = CurrencyWatchlistState(
                    error = result.message ?: "An unexpected error occurred."
                )
                is Resource.Loading -> _state.value =
                    CurrencyWatchlistState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }

    fun insertCurrency(currencyWatchlistItem: CurrencyWatchlistItem) {
        insertCurrencyWatchlistItemUseCase(currencyWatchlistItem).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    getCurrencies()
                }
                is Resource.Error -> _state.value = CurrencyWatchlistState(
                    error = result.message ?: "An unexpected error occurred."
                )
                is Resource.Loading -> _state.value =
                    CurrencyWatchlistState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }

    fun deleteCurrency(currencyWatchlistItemUid: Int) {
        deleteCurrencyWatchlistItemUseCase(currencyWatchlistItemUid).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    getCurrencies()
                }
                is Resource.Error -> _state.value = CurrencyWatchlistState(
                    error = result.message ?: "An unexpected error occurred."
                )
                is Resource.Loading -> _state.value =
                    CurrencyWatchlistState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }
}