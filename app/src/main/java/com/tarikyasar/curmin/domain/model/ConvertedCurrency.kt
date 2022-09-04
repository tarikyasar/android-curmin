package com.tarikyasar.curmin.domain.model

data class ConvertedCurrency(
    val fromCurrencyCode: String,
    val toCurrencyCode: String,
    val amount: Double,
    val rate: Double
)
