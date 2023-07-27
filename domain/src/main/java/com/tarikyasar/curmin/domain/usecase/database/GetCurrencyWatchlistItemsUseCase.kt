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

class GetCurrencyWatchlistItemsUseCase @Inject constructor(
    private val database: AppDatabase
) {
    operator fun invoke(): Flow<Resource<List<CurrencyWatchlistItemData>>> = flow {
        try {
            emit(Resource.Loading<List<CurrencyWatchlistItemData>>())
            val currencyWatchlistItems = database.currencyDao().getAll()
            emit(Resource.Success<List<CurrencyWatchlistItemData>>(currencyWatchlistItems))
        } catch (e: IOException) {
            emit(
                Resource.Error<List<CurrencyWatchlistItemData>>(
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