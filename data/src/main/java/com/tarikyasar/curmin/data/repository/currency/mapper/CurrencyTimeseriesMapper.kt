package com.tarikyasar.curmin.data.repository.currency.mapper

fun com.tarikyasar.curmin.domain.model.timeseries.CurrencyTimeSeries.toLineData(): com.tarikyasar.curmin.domain.model.timeseries.CurrencyTimeSeries {
    val rates = mutableListOf<Pair<Int, Double>>()

    this.rates
        .toString()
        .split(", ")
        .forEachIndexed { i, string ->
            val rate = string.split("=").lastOrNull()?.replace("}", "")?.toDouble()
            rates.add(
                Pair(i + 1, (rate ?: 0.0))
            )
        }

    return com.tarikyasar.curmin.domain.model.timeseries.CurrencyTimeSeries(
        base = base,
        rates = rates
    )
}