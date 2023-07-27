package com.tarikyasar.curmin.domain.usecase.database

import com.tarikyasar.curmin.domain.database.AppDatabase
import com.tarikyasar.curmin.domain.model.CurminError
import com.tarikyasar.curmin.domain.model.CurminErrorType
import com.tarikyasar.curmin.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class DeleteCurrencyWatchlistItemUseCase @Inject constructor(
    private val database: AppDatabase
) {
    operator fun invoke(currencyWatchlistItemUid: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading<String>())
            database.currencyDao().delete(currencyWatchlistItemUid)
            emit(Resource.Success<String>(currencyWatchlistItemUid))
        } catch (e: IOException) {
            emit(Resource.Error<String>(CurminError(null, null, CurminErrorType.DATABASE_ERROR)))
        }
    }
}