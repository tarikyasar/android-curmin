package com.tarikyasar.curmin.presentation.ui.screens.currency_watchlist

import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.domain.model.CurminError
import com.tarikyasar.curmin.domain.model.Symbol

data class CurrencyWatchlistState(
    val error: CurminError? = null,
    val currencies: MutableList<CurrencyWatchlistItemData> = mutableListOf(),
    val symbols: List<Symbol> = emptyList(),
    val askToRemoveItemParameter: Boolean? = false,
    val isLoading: Boolean = false
)