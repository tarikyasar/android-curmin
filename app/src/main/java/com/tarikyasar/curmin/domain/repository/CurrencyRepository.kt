package com.tarikyasar.curmin.domain.repository

import com.tarikyasar.curmin.data.remote.dto.CurrencyConversionDto
import com.tarikyasar.curmin.data.remote.dto.symbol.CurrencySymbolsDto

interface CurrencyRepository {

    suspend fun getCurrencySymbols(): CurrencySymbolsDto

    suspend fun convertCurrency(
        fromCurrencySymbol: String,
        toCurrencySymbol: String,
        amount: Double,
    ): CurrencyConversionDto

}