package com.tarikyasar.curmin.domain.usecase.database

import com.tarikyasar.curmin.domain.database.AppDatabase
import com.tarikyasar.curmin.domain.database.model.CurrencyWatchlistItemData
import javax.inject.Inject

class UpdateCurrencyWatchlistItemUseCase @Inject constructor(
    private val database: AppDatabase
) {

    suspend operator fun invoke(currencyWatchlistItem: CurrencyWatchlistItemData) =
        database.currencyDao().updateCurrencyWatchlistItem(currencyWatchlistItem)
}