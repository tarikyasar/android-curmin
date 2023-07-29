package com.tarikyasar.curmin.data.remote.fluctuation

import com.google.gson.annotations.SerializedName
import com.tarikyasar.curmin.domain.model.fluctuation.CurrencyFluctuation
import java.io.Serializable

data class CurrencyFluctuationResponse(
    @SerializedName("change") val changeRounded: Double,
    @SerializedName("change_pct") val change: Double,
    @SerializedName("end_rate") val endRate: Double,
    @SerializedName("start_rate") val startRate: Double,
) : Serializable

fun CurrencyFluctuationResponse.toDomain(): CurrencyFluctuation {
    return CurrencyFluctuation(
        changeRounded = changeRounded,
        change = change,
        endRate = endRate,
        startRate = startRate
    )
}