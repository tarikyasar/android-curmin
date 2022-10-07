package com.tarikyasar.curmin.presentation.screens.currency_watchlist

import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.domain.model.Symbol

data class CurrencyWatchlistState(
    val isLoading: Boolean = false,
    val error: String = "",
    var currencies: MutableList<CurrencyWatchlistItemData> = mutableListOf(),
    var askToRemoveItemParameter: Boolean? = false,
    var currencySymbols: List<Symbol> = emptyList()
)