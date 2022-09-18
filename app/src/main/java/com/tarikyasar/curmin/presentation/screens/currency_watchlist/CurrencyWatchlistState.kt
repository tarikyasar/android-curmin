package com.tarikyasar.curmin.presentation.screens.currency_watchlist

import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData

data class CurrencyWatchlistState(
    val isLoading: Boolean = false,
    val error: String = "",
    var currencies: MutableList<CurrencyWatchlistItemData> = mutableListOf()
)