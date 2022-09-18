package com.tarikyasar.curmin.domain.usecase.database

import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.data.database.AppDatabase
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class InsertCurrencyWatchlistItemUseCase @Inject constructor(
    private val database: AppDatabase
) {
    operator fun invoke(currencyWatchlistItem: CurrencyWatchlistItemData): Flow<Resource<CurrencyWatchlistItemData>> = flow {
        try {
            emit(Resource.Loading<CurrencyWatchlistItemData>())
            database.currencyDao().insert(currencyWatchlistItem)
            emit(Resource.Success<CurrencyWatchlistItemData>(currencyWatchlistItem))
        } catch (e: HttpException) {
            emit(
                Resource.Error<CurrencyWatchlistItemData>(
                    e.localizedMessage ?: "An unexpected error occurred."
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<CurrencyWatchlistItemData>("Couldn't reach database."))
        }
    }
}