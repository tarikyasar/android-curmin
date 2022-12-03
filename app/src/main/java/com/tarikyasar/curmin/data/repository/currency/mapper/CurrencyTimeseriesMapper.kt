package com.tarikyasar.curmin.data.repository.currency.mapper

import com.himanshoe.charty.line.model.LineData
import com.tarikyasar.curmin.data.remote.dto.timeseries.CurrencyTimeseriesDto
import com.tarikyasar.curmin.domain.model.CurrencyTimeseries

fun CurrencyTimeseriesDto.toLineData(): CurrencyTimeseries {
    val rates = mutableListOf<LineData>()

    this.rates
        .toString()
        .split(", ")
        .forEachIndexed { i, string ->
            val rate = string.split("=").lastOrNull()?.replace("}", "")?.toDouble()
            rates.add(
                LineData(i, (rate ?: 0.0).toFloat())
            )
        }

    return CurrencyTimeseries(
        rates = rates
    )
}