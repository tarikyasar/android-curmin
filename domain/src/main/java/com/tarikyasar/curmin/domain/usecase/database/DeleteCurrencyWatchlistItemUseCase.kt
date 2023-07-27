package com.tarikyasar.curmin.domain.usecase.database

import com.tarikyasar.curmin.domain.database.AppDatabase
import javax.inject.Inject

class DeleteCurrencyWatchlistItemUseCase @Inject constructor(
    private val database: AppDatabase
) {
    suspend operator fun invoke(currencyWatchlistItemUid: String) =
        database.currencyDao().delete(currencyWatchlistItemUid)
}