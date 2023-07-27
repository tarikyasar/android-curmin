package com.tarikyasar.curmin.domain.usecase.database

import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.domain.database.AppDatabase
import javax.inject.Inject

class GetCurrencyWatchlistItemsUseCase @Inject constructor(
    private val database: AppDatabase
) {
    suspend operator fun invoke(): List<CurrencyWatchlistItemData> {
        return database.currencyDao().getAll()
    }
}