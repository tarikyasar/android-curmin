package com.tarikyasar.curmin.domain.usecase.api

import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.data.repository.currency.mapper.toLineData
import com.tarikyasar.curmin.domain.model.CurrencyTimeseries
import com.tarikyasar.curmin.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrencyTimeseriesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke(
        startDate: String,
        endDate: String,
        baseCurrencyCode: String,
        targetCurrencyCode: String
    ): Flow<Resource<CurrencyTimeseries>> = flow {
        try {
            emit(Resource.Loading<CurrencyTimeseries>())
            val convertedCurrency = repository.getCurrencyTimeseriesData(
                startDate = startDate,
                endDate = endDate,
                baseCurrencyCode = baseCurrencyCode,
                targetCurrencyCode = targetCurrencyCode
            )
            emit(Resource.Success<CurrencyTimeseries>(convertedCurrency.toLineData()))
        } catch (e: HttpException) {
            emit(
                Resource.Error<CurrencyTimeseries>(
                    e.localizedMessage ?: "An unexpected error occurred."
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<CurrencyTimeseries>("Couldn't reach server. Check your internet connection."))
        }
    }
}