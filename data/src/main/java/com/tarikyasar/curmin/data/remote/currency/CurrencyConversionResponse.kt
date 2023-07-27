package com.tarikyasar.curmin.data.remote.currency

import com.tarikyasar.curmin.domain.model.currency.CurrencyConversion

data class CurrencyConversionResponse(
    val info: InfoResponse,
    val query: QueryResponse,
    val result: Double,
)

fun CurrencyConversionResponse.toDomain(): CurrencyConversion {
    return CurrencyConversion(
        info = info.toDomain(),
        query = query.toDomain(),
        result = result
    )
}