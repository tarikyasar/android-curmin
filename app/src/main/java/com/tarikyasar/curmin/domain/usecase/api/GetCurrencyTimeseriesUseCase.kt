package com.tarikyasar.curmin.domain.usecase.api

import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.data.repository.currency.mapper.toLineData
import com.tarikyasar.curmin.domain.model.CurminError
import com.tarikyasar.curmin.domain.model.CurminErrorType
import com.tarikyasar.curmin.domain.model.CurrencyTimeSeries
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
    ): Flow<Resource<CurrencyTimeSeries>> = flow {
        try {
            emit(Resource.Loading<CurrencyTimeSeries>())
            val convertedCurrency = repository.getCurrencyTimeseriesData(
                startDate = startDate,
                endDate = endDate,
                baseCurrencyCode = baseCurrencyCode,
                targetCurrencyCode = targetCurrencyCode
            )
            emit(Resource.Success<CurrencyTimeSeries>(convertedCurrency.toLineData()))
        } catch (e: HttpException) {
            emit(
                Resource.Error<CurrencyTimeSeries>(
                    CurminError(e.code(), null, CurminErrorType.API_ERROR)
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error<CurrencyTimeSeries>(
                    CurminError(
                        null,
                        null,
                        CurminErrorType.NETWORK_ERROR
                    )
                )
            )
        }
    }
}