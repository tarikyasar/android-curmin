package com.tarikyasar.curmin.presentation.latest_currency_data

import com.tarikyasar.curmin.domain.model.LatestData

data class LatestCurrencyDataState(
    val isLoading: Boolean = false,
    val latestData: LatestData? = null,
    val error: String = ""
)