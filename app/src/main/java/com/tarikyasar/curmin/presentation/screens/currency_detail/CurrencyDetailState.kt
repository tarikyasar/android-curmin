package com.tarikyasar.curmin.presentation.screens.currency_detail

import com.himanshoe.charty.line.model.LineData

data class CurrencyDetailState(
    val error: String = "",
    val isLoading: Boolean = false,
    val currencyRates: List<LineData> = emptyList()
)