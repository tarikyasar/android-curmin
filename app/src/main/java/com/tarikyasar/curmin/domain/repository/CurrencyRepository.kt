package com.tarikyasar.curmin.domain.repository

import com.tarikyasar.curmin.data.remote.dto.currency.CurrencyConversionDto
import com.tarikyasar.curmin.data.remote.dto.fluctuation.CurrencyFluctuationDto
import com.tarikyasar.curmin.data.remote.dto.symbol.CurrencySymbolsDto
import com.tarikyasar.curmin.data.remote.dto.timeseries.CurrencyTimeseriesDto

interface CurrencyRepository {

    suspend fun getCurrencySymbols(): CurrencySymbolsDto

    suspend fun convertCurrency(
        fromCurrencySymbol: String,
        toCurrencySymbol: String,
        amount: Double,
    ): CurrencyConversionDto

    suspend fun getCurrencyTimeseriesData(
        startDate: String,
        endDate: String,
        baseCurrencyCode: String,
        targetCurrencyCode: String
    ): CurrencyTimeseriesDto

    suspend fun getCurrencyFluctuation(
        startDate: String,
        endDate: String,
        baseCurrencyCode: String,
        targetCurrencyCode: String
    ): CurrencyFluctuationDto
}