package com.tarikyasar.curmin.domain.usecase

import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.data.repository.currency.mapper.toCurrency
import com.tarikyasar.curmin.domain.model.LatestData
import com.tarikyasar.curmin.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetLatestCurrencyData @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke(
        base: String,
        currencies: List<String>,
    ): Flow<Resource<LatestData>> = flow {
        try {
            var currencyString = ""

            currencies.forEach {
                currencyString += "$it,"
            }
            emit(Resource.Loading<LatestData>())
            val convertedCurrency = repository.getLatestCurrency(
                base = base,
                currencies = currencyString.dropLast(1)
            )
            emit(Resource.Success<LatestData>(convertedCurrency.toCurrency()))
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