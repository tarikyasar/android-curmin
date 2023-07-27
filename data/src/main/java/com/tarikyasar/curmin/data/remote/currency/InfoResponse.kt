package com.tarikyasar.curmin.data.remote.currency

import com.tarikyasar.curmin.domain.model.currency.Info

data class InfoResponse(
    val rate: Double,
    val timestamp: Int
)

fun InfoResponse.toDomain(): Info {
    return Info(
        rate = rate,
        timestamp = timestamp
    )
}