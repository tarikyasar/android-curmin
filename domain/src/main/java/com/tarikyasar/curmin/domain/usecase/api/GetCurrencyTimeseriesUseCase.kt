package com.tarikyasar.curmin.domain.usecase.api

import com.tarikyasar.curmin.domain.model.timeseries.CurrencyTimeSeries
import com.tarikyasar.curmin.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrencyTimeseriesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {

    suspend operator fun invoke(
        startDate: String,
        endDate: String,
        baseCurrencyCode: String,
        targetCurrencyCode: String
    ): CurrencyTimeSeries {
        return repository.getCurrencyTimeSeriesData(
            startDate = startDate,
            endDate = endDate,
            baseCurrencyCode = baseCurrencyCode,
            targetCurrencyCode = targetCurrencyCode
        )
    }
}