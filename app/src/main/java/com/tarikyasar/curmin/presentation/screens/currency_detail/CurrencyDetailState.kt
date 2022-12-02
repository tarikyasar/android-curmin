package com.tarikyasar.curmin.presentation.screens.currency_detail

data class CurrencyDetailState(
    val error: String = "",
    val isLoading: Boolean = false,
    val currencyRates: List<Double> = emptyList()
)