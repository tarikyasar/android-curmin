package com.tarikyasar.curmin.domain.usecase.database

import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.domain.database.AppDatabase
import com.tarikyasar.curmin.domain.model.CurminError
import com.tarikyasar.curmin.domain.model.CurminErrorType
import com.tarikyasar.curmin.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetCurrencyWatchlistItemUseCase @Inject constructor(
    private val database: AppDatabase
) {
    operator fun invoke(currencyWatchlistItemUid: String): Flow<Resource<CurrencyWatchlistItemData>> =
        flow {
            try {
                emit(Resource.Loading<CurrencyWatchlistItemData>())
                val currencyWatchlistItem =
                    database.currencyDao().getCurrencyWatchlistItem(currencyWatchlistItemUid)
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