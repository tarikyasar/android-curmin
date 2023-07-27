package com.tarikyasar.curmin.presentation.ui.screens.currency.detail

import com.tarikyasar.curmin.domain.model.CurminError
import com.tarikyasar.curmin.domain.model.CurrencyTimeSeries

interface CurrencyDetailContract {
    data class UiState(
        val error: CurminError? = null,
        val currencyRates: CurrencyTimeSeries? = null
    )

    sealed class Intent {
        data class GetCurrencyTimeSeries(
            val startDate: String,
            val endDate: String,
            val baseCurrencyCode: String,
            val targetCurrencyCode: String
        ) : Intent()
    }

    sealed class Event
}