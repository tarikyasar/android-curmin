package com.tarikyasar.curmin.presentation.ui.screens.currency.detail

import com.tarikyasar.curmin.domain.usecase.api.GetCurrencyTimeSeriesUseCase
import com.tarikyasar.curmin.presentation.ui.base.BaseViewModel
import com.tarikyasar.curmin.presentation.ui.screens.currency.detail.CurrencyDetailContract.Event
import com.tarikyasar.curmin.presentation.ui.screens.currency.detail.CurrencyDetailContract.Intent
import com.tarikyasar.curmin.presentation.ui.screens.currency.detail.CurrencyDetailContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyDetailViewModel @Inject constructor(
    private val getCurrencyTimeSeriesUseCase: GetCurrencyTimeSeriesUseCase
) : BaseViewModel<UiState, Intent, Event>(UiState()) {

    fun getCurrencyTimeSeries(
        startDate: String,
        endDate: String,
        baseCurrencyCode: String,
        targetCurrencyCode: String
    ) {
        withUseCaseScope {
            val currencyTimeSeries = getCurrencyTimeSeriesUseCase(
                startDate = startDate,
                endDate = endDate,
                baseCurrencyCode = baseCurrencyCode,
                targetCurrencyCode = targetCurrencyCode
            )

            updateUiState {
                copy(
                    currencyRates = currencyTimeSeries
                )
            }
        }
    }

    override fun onIntent(intent: Intent) {
        TODO("Not yet implemented")
    }
}