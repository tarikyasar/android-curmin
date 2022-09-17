package com.tarikyasar.curmin.domain.usecase.database

import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.data.database.AppDatabase
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeleteCurrencyWatchlistItemUseCase @Inject constructor(
    private val database: AppDatabase
) {
    operator fun invoke(currencyWatchlistItemUid: Int): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.Loading<Int>())
            database.currencyDao().delete(currencyWatchlistItemUid)
            emit(Resource.Success<Int>(currencyWatchlistItemUid))
        } catch (e: HttpException) {
            emit(
                Resource.Error<Int>(
                    e.localizedMessage ?: "An unexpected error occurred."
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<Int>("Couldn't reach database."))
        }
    }
}