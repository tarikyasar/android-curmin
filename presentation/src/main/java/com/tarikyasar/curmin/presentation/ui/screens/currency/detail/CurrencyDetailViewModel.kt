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

    override fun onIntent(intent: Intent) {
        when (intent) {
            is Intent.GetCurrencyTimeSeries -> {
                withUseCaseScope {
                    val currencyTimeSeries = getCurrencyTimeSeriesUseCase(
                        startDate = intent.startDate,
                        endDate = intent.endDate,
                        baseCurrencyCode = intent.baseCurrencyCode,
                        targetCurrencyCode = intent.targetCurrencyCode
                    )

                    updateUiState {
                        copy(
                            currencyRates = currencyTimeSeries
                        )
                    }
                }
            }
        }
    }
}