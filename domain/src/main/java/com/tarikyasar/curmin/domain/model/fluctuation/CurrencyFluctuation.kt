package com.tarikyasar.curmin.domain.model.fluctuation

data class CurrencyFluctuation(
    val changeRounded: Double,
    val change: Double,
    val endRate: Double,
    val startRate: Double,
)