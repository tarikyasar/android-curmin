package com.tarikyasar.curmin.data.remote

import com.tarikyasar.curmin.data.remote.dto.CurrencyConversionDto
import com.tarikyasar.curmin.data.remote.dto.symbol.CurrencySymbolsDto
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
}