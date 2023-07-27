package com.tarikyasar.curmin.domain.usecase.api

import com.tarikyasar.curmin.domain.model.CurminError
import com.tarikyasar.curmin.domain.model.CurminErrorType
import com.tarikyasar.curmin.domain.model.currency.CurrencyConversion
import com.tarikyasar.curmin.domain.model.fluctuation.CurrencyFluctuationRates
import com.tarikyasar.curmin.domain.repository.CurrencyRepository
import com.tarikyasar.curmin.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrencyFluctuationUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {

    suspend operator fun invoke(
        startDate: String,
        endDate: String,
        baseCurrencyCode: String,
        targetCurrencyCode: String
    ): CurrencyFluctuationRates {
        return repository.getCurrencyFluctuation(
            startDate = startDate,
            endDate = endDate,
            baseCurrencyCode = baseCurrencyCode,
            targetCurrencyCode = targetCurrencyCode
        )
    }
}