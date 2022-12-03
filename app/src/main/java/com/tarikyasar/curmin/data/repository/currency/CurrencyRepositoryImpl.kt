package com.tarikyasar.curmin.data.repository.currency

import com.tarikyasar.curmin.data.remote.CurrencyApi
import com.tarikyasar.curmin.data.remote.dto.currency.CurrencyConversionDto
import com.tarikyasar.curmin.data.remote.dto.symbol.CurrencySymbolsDto
import com.tarikyasar.curmin.data.remote.dto.timeseries.CurrencyTimeseriesDto
import com.tarikyasar.curmin.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val api: CurrencyApi
) : CurrencyRepository {

    override suspend fun getCurrencySymbols(): CurrencySymbolsDto {
        return api.getCurrencySymbols()
    }

    override suspend fun convertCurrency(
        fromCurrencySymbol: String,
        toCurrencySymbol: String,
        amount: Double
    ): CurrencyConversionDto {
        return api.convertCurrency(
            fromCurrencySymbol = fromCurrencySymbol,
            toCurrencySymbol = toCurrencySymbol,
            amount = amount
        )
    }

    override suspend fun getCurrencyTimeseriesData(
        startDate: String,
        endDate: String,
        baseCurrencyCode: String,
        targetCurrencyCode: String
    ): CurrencyTimeseriesDto {
        return api.getCurrencyTimeseries(
            startDate = startDate,
            endDate = endDate,
            baseCurrencyCode = baseCurrencyCode,
            targetCurrencyCode = targetCurrencyCode
        )
    }
}