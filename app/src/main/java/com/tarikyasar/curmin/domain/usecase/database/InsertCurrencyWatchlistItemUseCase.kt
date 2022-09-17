package com.tarikyasar.curmin.domain.usecase.database

import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.data.database.AppDatabase
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class InsertCurrencyWatchlistItemUseCase @Inject constructor(
    private val database: AppDatabase
) {
    operator fun invoke(currencyWatchlistItem: CurrencyWatchlistItem): Flow<Resource<CurrencyWatchlistItem>> = flow {
        try {
            emit(Resource.Loading<CurrencyWatchlistItem>())
            database.currencyDao().insert(currencyWatchlistItem)
            emit(Resource.Success<CurrencyWatchlistItem>(currencyWatchlistItem))
        } catch (e: HttpException) {
            emit(
                Resource.Error<CurrencyWatchlistItem>(
                    e.localizedMessage ?: "An unexpected error occurred."
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<CurrencyWatchlistItem>("Couldn't reach database."))
        }
    }
}