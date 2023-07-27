package com.tarikyasar.curmin.data.remote

import com.tarikyasar.curmin.data.remote.currency.CurrencyConversionResponse
import com.tarikyasar.curmin.data.remote.fluctuation.CurrencyFluctuationRatesResponse
import com.tarikyasar.curmin.data.remote.timeseries.CurrencyTimeSeriesResponse
import com.tarikyasar.curmin.domain.model.symbol.CurrencySymbols
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/exchangerates_data/symbols")
    suspend fun getCurrencySymbols(): CurrencySymbols

    @GET("/exchangerates_data/convert")
    suspend fun convertCurrency(
        @Query("from") fromCurrencySymbol: String,
        @Query("to") toCurrencySymbol: String,
        @Query("amount") amount: Double,
    ): CurrencyConversionResponse

    @GET("/exchangerates_data/timeseries")
    suspend fun getCurrencyTimeSeries(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("base") baseCurrencyCode: String,
        @Query("symbols") targetCurrencyCode: String,
    ): CurrencyTimeSeriesResponse

    @GET("/exchangerates_data/fluctuation")
    suspend fun getCurrencyFluctuation(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("base") baseCurrencyCode: String,
        @Query("symbols") targetCurrencyCode: String,
    ): CurrencyFluctuationRatesResponse
}