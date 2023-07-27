package com.tarikyasar.curmin.data.remote.timeseries

import com.tarikyasar.curmin.domain.model.timeseries.CurrencyTimeSeries

data class CurrencyTimeSeriesResponse(
    val base: String,
    val rates: Any
)

fun CurrencyTimeSeriesResponse.toDomain(): CurrencyTimeSeries {
    return CurrencyTimeSeries(
        base = base,
        rates = rates
    )
}