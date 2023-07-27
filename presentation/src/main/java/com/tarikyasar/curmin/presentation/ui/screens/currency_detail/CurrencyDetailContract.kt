package com.tarikyasar.curmin.presentation.ui.screens.currency_detail

import com.tarikyasar.curmin.domain.model.CurminError
import com.tarikyasar.curmin.domain.model.CurrencyTimeSeries

interface CurrencyDetailContract {
    data class UiState(
        val error: CurminError? = null,
        val isLoading: Boolean = false,
        val currencyRates: CurrencyTimeSeries? = null
    )

    sealed class Intent

    sealed class Event
}