package com.tarikyasar.curmin.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CurrencyFluctuation(
    @SerializedName("change") val changeRounded: Double,
    @SerializedName("change_pct") val change: Double,
    @SerializedName("end_rate") val endRate: Double,
    @SerializedName("start_rate") val startRate: Double,
) : Serializable

