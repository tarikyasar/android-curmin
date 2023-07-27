package com.tarikyasar.curmin.data.repository.currency

import com.tarikyasar.curmin.data.remote.CurrencyApi
import com.tarikyasar.curmin.data.remote.currency.toDomain
import com.tarikyasar.curmin.data.remote.fluctuation.toDomain
import com.tarikyasar.curmin.data.remote.timeseries.toDomain
import com.tarikyasar.curmin.domain.model.currency.CurrencyConversion
import com.tarikyasar.curmin.domain.model.fluctuation.CurrencyFluctuationRates
import com.tarikyasar.curmin.domain.model.symbol.CurrencySymbols
import com.tarikyasar.curmin.domain.model.timeseries.CurrencyTimeSeries
import com.tarikyasar.curmin.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val api: CurrencyApi
) : CurrencyRepository {
    override suspend fun getCurrencySymbols(): CurrencySymbols {
        return api.getCurrencySymbols()
    }

    override suspend fun convertCurrency(
        fromCurrencySymbol: String,
        toCurrencySymbol: String,
        amount: Double
    ): CurrencyConversion {
        return api.convertCurrency(
            fromCurrencySymbol = fromCurrencySymbol,
            toCurrencySymbol = toCurrencySymbol,
            amount = amount
        ).toDomain()
    }

    override suspend fun getCurrencyTimeSeriesData(
        startDate: String,
        endDate: String,
        baseCurrencyCode: String,
        targetCurrencyCode: String
    ): CurrencyTimeSeries {
        return api.getCurrencyTimeseries(
            startDate = startDate,
            endDate = endDate,
            baseCurrencyCode = baseCurrencyCode,
            targetCurrencyCode = targetCurrencyCode
        ).toDomain()
    }

    override suspend fun getCurrencyFluctuation(
        startDate: String,
        endDate: String,
        baseCurrencyCode: String,
        targetCurrencyCode: String
    ): CurrencyFluctuationRates {
        return api.getCurrencyFluctuation(
            startDate = startDate,
            endDate = endDate,
            baseCurrencyCode = baseCurrencyCode,
            targetCurrencyCode = targetCurrencyCode
        ).toDomain()
    }

}