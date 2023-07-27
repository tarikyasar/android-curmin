package com.tarikyasar.curmin.data.remote.fluctuation

import com.google.gson.annotations.SerializedName
import com.tarikyasar.curmin.domain.model.fluctuation.CurrencyFluctuationRates
import java.io.Serializable

data class CurrencyFluctuationResponse(
    @SerializedName("change") val changeRounded: Double,
    @SerializedName("change_pct") val change: Double,
    @SerializedName("end_rate") val endRate: Double,
    @SerializedName("start_rate") val startRate: Double,
) : Serializable