package com.tarikyasar.curmin.data.remote

import com.tarikyasar.curmin.data.remote.dto.currency.CurrencyConversionDto
import com.tarikyasar.curmin.data.remote.dto.fluctuation.CurrencyFluctuationDto
import com.tarikyasar.curmin.data.remote.dto.symbol.CurrencySymbolsDto
import com.tarikyasar.curmin.data.remote.dto.timeseries.CurrencyTimeseriesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/exchangerates_data/symbols")
    suspend fun getCurrencySymbols(): CurrencySymbolsDto

    @GET("/exchangerates_data/convert")
    suspend fun convertCurrency(
        @Query("from") fromCurrencySymbol: String,
        @Query("to") toCurrencySymbol: String,
        @Query("amount") amount: Double,
    ): CurrencyConversionDto

    @GET("/exchangerates_data/timeseries")
    suspend fun getCurrencyTimeseries(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("base") baseCurrencyCode: String,
        @Query("symbols") targetCurrencyCode: String,
    ): CurrencyTimeseriesDto

    @GET("/exchangerates_data/fluctuation")
    suspend fun getCurrencyFluctuation(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("base") baseCurrencyCode: String,
        @Query("symbols") targetCurrencyCode: String,
    ): CurrencyFluctuationDto
}