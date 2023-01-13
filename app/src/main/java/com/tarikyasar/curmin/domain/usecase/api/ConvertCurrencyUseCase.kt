package com.tarikyasar.curmin.domain.usecase.api

import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.data.repository.currency.mapper.toConvertedCurrency
import com.tarikyasar.curmin.domain.model.ConvertedCurrency
import com.tarikyasar.curmin.domain.model.CurminError
import com.tarikyasar.curmin.domain.model.CurminErrorType
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
            emit(
                Resource.Error<ConvertedCurrency>(
                    CurminError(
                        e.code(),
                        null,
                        CurminErrorType.API_ERROR
                    )
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error<ConvertedCurrency>(
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