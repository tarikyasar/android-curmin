package com.tarikyasar.curmin.domain.repository

import com.tarikyasar.curmin.domain.model.CurrencyTimeSeries
import com.tarikyasar.curmin.domain.model.currency.CurrencyConversion
import com.tarikyasar.curmin.domain.model.fluctuation.CurrencyFluctuation
import com.tarikyasar.curmin.domain.model.symbol.CurrencySymbols

interface CurrencyRepository {

    suspend fun getCurrencySymbols(): CurrencySymbols

    suspend fun convertCurrency(
        fromCurrencySymbol: String,
        toCurrencySymbol: String,
        amount: Double,
    ): CurrencyConversion

    suspend fun getCurrencyTimeSeriesData(
        startDate: String,
        endDate: String,
        baseCurrencyCode: String,
        targetCurrencyCode: String
    ): CurrencyTimeSeries

    suspend fun getCurrencyFluctuation(
        startDate: String,
        endDate: String,
        baseCurrencyCode: String,
        targetCurrencyCode: String
    ): CurrencyFluctuation
}