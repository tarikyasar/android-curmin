package com.tarikyasar.curmin.presentation.convert_currency

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.domain.usecase.api.ConvertCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ConvertCurrencyViewModel @Inject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ConvertCurrencyState())
    val state: State<ConvertCurrencyState> = _state

    fun getSymbols(
        fromCurrencySymbol: String,
        toCurrencySymbol: String,
        amount: Double
    ) {
        convertCurrencyUseCase(
            fromCurrencySymbol = fromCurrencySymbol,
            toCurrencySymbol = toCurrencySymbol,
            amount = amount
        ).onEach { result ->
            when (result) {
                is Resource.Success -> _state.value = ConvertCurrencyState(convertedCurrency = result.data)
                is Resource.Error -> _state.value = ConvertCurrencyState(error = result.message ?:  "An unexpected error occurred.")
                is Resource.Loading -> _state.value = ConvertCurrencyState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }
}