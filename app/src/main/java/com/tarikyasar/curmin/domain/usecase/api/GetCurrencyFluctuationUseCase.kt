package com.tarikyasar.curmin.domain.usecase.api

import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.data.repository.currency.mapper.toCurrencyFluctuation
import com.tarikyasar.curmin.domain.model.CurrencyFluctuation
import com.tarikyasar.curmin.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrencyFluctuationUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke(
        startDate: String,
        endDate: String,
        baseCurrencyCode: String,
        targetCurrencyCode: String
    ): Flow<Resource<CurrencyFluctuation>> = flow {
        try {
            emit(Resource.Loading<CurrencyFluctuation>())
            val convertedCurrency = repository.getCurrencyFluctuation(
                startDate = startDate,
                endDate = endDate,
                baseCurrencyCode = baseCurrencyCode,
                targetCurrencyCode = targetCurrencyCode
            )
            emit(Resource.Success<CurrencyFluctuation>(convertedCurrency.toCurrencyFluctuation()))
        } catch (e: HttpException) {
            emit(
                Resource.Error<CurrencyFluctuation>(
                    e.localizedMessage ?: "An unexpected error occurred."
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<CurrencyFluctuation>("Couldn't reach server. Check your internet connection."))
        }
    }
}