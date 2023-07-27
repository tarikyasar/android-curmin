package com.tarikyasar.curmin.data.remote.timeseries

import com.tarikyasar.curmin.domain.model.CurrencyTimeSeries

data class CurrencyTimeSeriesResponse(
    val base: String,
    val rates: Any
)

fun CurrencyTimeSeriesResponse.toDomain(): CurrencyTimeSeries {
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

    return CurrencyTimeSeries(
        rates = rates
    )
}