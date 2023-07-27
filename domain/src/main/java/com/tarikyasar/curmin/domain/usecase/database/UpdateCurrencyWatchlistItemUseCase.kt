package com.tarikyasar.curmin.domain.usecase.database

import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.domain.database.AppDatabase
import javax.inject.Inject

class UpdateCurrencyWatchlistItemUseCase @Inject constructor(
    private val database: AppDatabase
) {

    suspend operator fun invoke(currencyWatchlistItem: CurrencyWatchlistItemData) =
        database.currencyDao().updateCurrencyWatchlistItem(currencyWatchlistItem)
}