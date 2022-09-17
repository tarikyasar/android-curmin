package com.tarikyasar.curmin.presentation.currency_symbol_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.domain.usecase.api.GetCurrencySymbolsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CurrencySymbolListDropDownViewModel @Inject constructor(
    private val getCurrencySymbolsUseCase: GetCurrencySymbolsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CurrencySymbolListDropDownState())
    val state: State<CurrencySymbolListDropDownState> = _state

    init {
        getSymbols()
    }

    private fun getSymbols() {
        getCurrencySymbolsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> _state.value =
                    CurrencySymbolListDropDownState(currencySymbols = result.data ?: emptyList())
                is Resource.Error -> _state.value = CurrencySymbolListDropDownState(
                    error = result.message ?: "An unexpected error occurred."
                )
                is Resource.Loading -> _state.value =
                    CurrencySymbolListDropDownState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }
}