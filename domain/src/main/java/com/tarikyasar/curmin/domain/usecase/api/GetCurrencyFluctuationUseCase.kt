package com.tarikyasar.curmin.domain.usecase.api

import com.tarikyasar.curmin.domain.model.fluctuation.CurrencyFluctuation
import com.tarikyasar.curmin.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrencyFluctuationUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {

    suspend operator fun invoke(
        startDate: String,
        endDate: String,
        baseCurrencyCode: String,
        targetCurrencyCode: String
    ): CurrencyFluctuation {
        return repository.getCurrencyFluctuation(
            startDate = startDate,
            endDate = endDate,
            baseCurrencyCode = baseCurrencyCode,
            targetCurrencyCode = targetCurrencyCode
        )
    }
}