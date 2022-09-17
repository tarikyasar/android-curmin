package com.tarikyasar.curmin.domain.usecase.database

import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.data.database.AppDatabase
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrencyWatchlistItemsUseCase @Inject constructor(
    private val database: AppDatabase
) {
    operator fun invoke(): Flow<Resource<List<CurrencyWatchlistItem>>> = flow {
        try {
            emit(Resource.Loading<List<CurrencyWatchlistItem>>())
            val currencyWatchlistItems = database.currencyDao().getAll()
            emit(Resource.Success<List<CurrencyWatchlistItem>>(currencyWatchlistItems))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<CurrencyWatchlistItem>>(
                    e.localizedMessage ?: "An unexpected error occurred."
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<List<CurrencyWatchlistItem>>("Couldn't reach database."))
        }
    }
}