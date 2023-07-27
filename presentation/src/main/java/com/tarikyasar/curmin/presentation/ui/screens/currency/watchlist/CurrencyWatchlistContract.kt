package com.tarikyasar.curmin.presentation.ui.screens.currency.watchlist

import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.domain.model.CurminError
import com.tarikyasar.curmin.domain.model.Symbol

interface CurrencyWatchlistContract {
    data class UiState(
        val error: CurminError? = null,
        val currencies: List<CurrencyWatchlistItemData> = mutableListOf(),
        val symbols: List<Symbol> = emptyList(),
        val askToRemoveItemParameter: Boolean? = false
    )

    sealed class Intent {
        object GetSymbols : Intent()

        object GetAskToRemoveItemParameter : Intent()

        data class GetCurrencies(val forceRefresh: Boolean) : Intent()

        data class DeleteCurrencyWatchlistItem(val currencyWatchlistItemUid: String) : Intent()

        data class UpdateCurrencyWatchlistItemData(val currencyWatchlistItem: CurrencyWatchlistItemData) :
            Intent()

        data class InsertCurrencyWatchlistItem(val currencyWatchlistItem: CurrencyWatchlistItemData) :
            Intent()

        data class GetCurrencyFluctuation(
            val startDate: String,
            val endDate: String,
            val baseCurrencyCode: String,
            val targetCurrencyCode: String,
            val currencyWatchlistItemData: CurrencyWatchlistItemData,
        ) : Intent()

        data class CreateCurrencyWatchlistItem(
            val baseCurrencyCode: String,
            val targetCurrencyCode: String
        ) : Intent()
    }

    sealed class Event
}