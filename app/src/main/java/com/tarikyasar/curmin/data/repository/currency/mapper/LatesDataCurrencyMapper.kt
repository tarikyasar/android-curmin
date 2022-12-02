package com.tarikyasar.curmin.data.repository.currency.mapper

import com.himanshoe.charty.line.model.LineData
import com.tarikyasar.curmin.data.remote.dto.latest.LatestDataDto
import com.tarikyasar.curmin.domain.model.LatestData

fun LatestDataDto.toLineData(): LatestData {
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

    return LatestData(
        rates = rates
    )
}