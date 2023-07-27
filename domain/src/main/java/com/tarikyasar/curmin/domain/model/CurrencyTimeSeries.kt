package com.tarikyasar.curmin.domain.model

data class CurrencyTimeSeries(
    val rates: List<Pair<Int, Double>>
)