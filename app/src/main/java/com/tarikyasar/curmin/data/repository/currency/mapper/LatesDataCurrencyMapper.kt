package com.tarikyasar.curmin.data.repository.currency.mapper

import com.tarikyasar.curmin.data.remote.dto.latest.LatestDataDto
import com.tarikyasar.curmin.domain.model.LatestData

fun LatestDataDto.toCurrency(): LatestData {
    val rates = mutableListOf<Double>()

    this.rates
        .toString()
        .split(", ")
        .forEach {
            val rate = it.split("=").lastOrNull()?.replace("}", "")?.toDouble()
            rates.add(
                rate ?: 0.0
            )
        }

    return LatestData(
        rates = rates
    )
}