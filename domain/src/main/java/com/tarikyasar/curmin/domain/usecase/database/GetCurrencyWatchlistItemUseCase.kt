package com.tarikyasar.curmin.domain.usecase.database

import com.tarikyasar.curmin.domain.database.AppDatabase
import com.tarikyasar.curmin.domain.database.model.CurrencyWatchlistItemData
import javax.inject.Inject

class GetCurrencyWatchlistItemUseCase @Inject constructor(
    private val database: AppDatabase
) {
    suspend operator fun invoke(currencyWatchlistItemUid: String): CurrencyWatchlistItemData {
        return database.currencyDao().getCurrencyWatchlistItem(currencyWatchlistItemUid)
    }
}