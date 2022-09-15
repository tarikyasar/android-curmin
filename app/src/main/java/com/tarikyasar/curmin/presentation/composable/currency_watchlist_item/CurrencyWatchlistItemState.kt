package com.tarikyasar.curmin.presentation.composable.currency_watchlist_item

import com.tarikyasar.curmin.domain.model.LatestData

data class CurrencyWatchlistItemState(
    val isLoading: Boolean = false,
    val latestData: LatestData? = null,
    val error: String = ""
)