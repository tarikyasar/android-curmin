package com.tarikyasar.curmin.presentation.screens.currency_watchlist

import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.domain.model.Symbol

data class CurrencyWatchlistState(
    val error: String = "",
    val currencies: MutableList<CurrencyWatchlistItemData> = mutableListOf(),
    val symbols: List<Symbol> = emptyList(),
    val askToRemoveItemParameter: Boolean? = false,
    val isLoading: Boolean = false
)