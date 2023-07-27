package com.tarikyasar.curmin.data.remote.currency

import com.tarikyasar.curmin.domain.model.currency.Query

data class QueryResponse(
    val amount: Int,
    val from: String,
    val to: String
)

fun QueryResponse.toDomain(): Query {
    return Query(
        amount = amount,
        from = from,
        to = to
    )
}