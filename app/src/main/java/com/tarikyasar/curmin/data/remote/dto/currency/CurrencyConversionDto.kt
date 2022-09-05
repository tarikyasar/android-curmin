package com.tarikyasar.curmin.data.remote.dto.currency

data class CurrencyConversionDto(
    val info: Info,
    val query: Query,
    val result: Double,
)