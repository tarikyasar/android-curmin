package com.tarikyasar.curmin.domain.usecase.database

import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.data.database.AppDatabase
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.domain.model.CurminError
import com.tarikyasar.curmin.domain.model.CurminErrorType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class UpdateCurrencyWatchlistItemUseCase @Inject constructor(
    private val database: AppDatabase
) {
    operator fun invoke(currencyWatchlistItem: CurrencyWatchlistItemData): Flow<Resource<CurrencyWatchlistItemData>> =
        flow {
            try {
                emit(Resource.Loading<CurrencyWatchlistItemData>())
                database.currencyDao().updateCurrencyWatchlistItem(currencyWatchlistItem)
                emit(Resource.Success<CurrencyWatchlistItemData>(currencyWatchlistItem))
            } catch (e: IOException) {
                emit(
                    Resource.Error<CurrencyWatchlistItemData>(
                        CurminError(
                            null,
                            null,
                            CurminErrorType.DATABASE_ERROR
                        )
                    )
                )
            }
        }
}