package com.tarikyasar.curmin.data.repository.currency.mapper

import com.tarikyasar.curmin.data.remote.dto.timeseries.CurrencyTimeseriesDto
import com.tarikyasar.curmin.domain.model.CurrencyTimeSeries

fun CurrencyTimeseriesDto.toLineData(): CurrencyTimeSeries {
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