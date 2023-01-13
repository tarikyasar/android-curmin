package com.tarikyasar.curmin.presentation.screens.currency_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.domain.usecase.api.GetCurrencyTimeseriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CurrencyDetailViewModel @Inject constructor(
    private val getCurrencyTimeseriesUseCase: GetCurrencyTimeseriesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CurrencyDetailState())
    val state: State<CurrencyDetailState> = _state

    fun getCurrencyTimeSeries(
        startDate: String,
        endDate: String,
        baseCurrencyCode: String,
        targetCurrencyCode: String
    ) {
        getCurrencyTimeseriesUseCase(
            startDate = startDate,
            endDate = endDate,
            baseCurrencyCode = baseCurrencyCode,
            targetCurrencyCode = targetCurrencyCode
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        currencyRates = result.data?.rates ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.error,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun resetError() {
        _state.value = _state.value.copy(
            error = null
        )
    }
}