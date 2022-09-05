package com.tarikyasar.curmin.presentation.latest_currency_data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.domain.usecase.GetCurrencySymbolsUseCase
import com.tarikyasar.curmin.domain.usecase.GetLatestCurrencyData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LatestCurrencyDataViewModel @Inject constructor(
    private val getLatestCurrencyData: GetLatestCurrencyData
) : ViewModel() {

    private val _state = mutableStateOf(LatestCurrencyDataState())
    val state: State<LatestCurrencyDataState> = _state

    init {
        getLatestData("USD", listOf("TRY", "EUR"))
    }

    private fun getLatestData(
        base: String,
        currencies: List<String>
    ) {
        getLatestCurrencyData(
            base = base,
            currencies = currencies
        ).onEach { result ->
            when (result) {
                is Resource.Success -> _state.value = LatestCurrencyDataState(latestData = result.data)
                is Resource.Error -> _state.value = LatestCurrencyDataState(error = result.message ?:  "An unexpected error occurred.")
                is Resource.Loading -> _state.value = LatestCurrencyDataState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }
}