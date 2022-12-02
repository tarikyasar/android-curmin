package com.tarikyasar.curmin.domain.usecase.api

import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.data.repository.currency.mapper.toLineData
import com.tarikyasar.curmin.domain.model.LatestData
import com.tarikyasar.curmin.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetLatestCurrencyDataUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke(
        startDate: String,
        endDate: String,
        baseCurrencyCode: String,
        targetCurrencyCode: String
    ): Flow<Resource<LatestData>> = flow {
        try {
            emit(Resource.Loading<LatestData>())
            val convertedCurrency = repository.getLatestCurrency(
                startDate = startDate,
                endDate = endDate,
                baseCurrencyCode = baseCurrencyCode,
                targetCurrencyCode = targetCurrencyCode
            )
            emit(Resource.Success<LatestData>(convertedCurrency.toLineData()))
        } catch (e: HttpException) {
            emit(
                Resource.Error<LatestData>(
                    e.localizedMessage ?: "An unexpected error occurred."
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<LatestData>("Couldn't reach server. Check your internet connection."))
        }
    }
}