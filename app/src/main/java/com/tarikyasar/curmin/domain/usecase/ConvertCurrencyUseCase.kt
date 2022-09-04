package com.tarikyasar.curmin.domain.usecase

import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.data.repository.mapper.toConvertedCurrency
import com.tarikyasar.curmin.data.repository.mapper.toCurrencySymbol
import com.tarikyasar.curmin.domain.model.ConvertedCurrency
import com.tarikyasar.curmin.domain.model.Symbol
import com.tarikyasar.curmin.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke(
        fromCurrencySymbol: String,
        toCurrencySymbol: String,
        amount: Double,
    ): Flow<Resource<ConvertedCurrency>> = flow {
        try {
            emit(Resource.Loading<ConvertedCurrency>())
            val convertedCurrency = repository.convertCurrency(
                fromCurrencySymbol = fromCurrencySymbol,
                toCurrencySymbol = toCurrencySymbol,
                amount = amount
            ).toConvertedCurrency()
            emit(Resource.Success<ConvertedCurrency>(convertedCurrency))
        } catch (e: HttpException) {
            emit(Resource.Error<ConvertedCurrency>(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error<ConvertedCurrency>("Couldn't reach server. Check your internet connection."))
        }
    }
}