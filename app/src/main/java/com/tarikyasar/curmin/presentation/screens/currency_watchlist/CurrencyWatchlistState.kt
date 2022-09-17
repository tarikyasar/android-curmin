package com.tarikyasar.curmin.presentation.screens.currency_watchlist

import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItem

data class CurrencyWatchlistState(
    val isLoading: Boolean = false,
    val error: String = "",
    val currencies: List<CurrencyWatchlistItem> = emptyList()
)