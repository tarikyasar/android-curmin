package com.tarikyasar.curmin.domain.model

import com.himanshoe.charty.line.model.LineData

data class CurrencyTimeseries(
    val rates: List<LineData>
)