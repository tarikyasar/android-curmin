package com.tarikyasar.curmin.data.repository

import com.tarikyasar.curmin.data.remote.CurrencyApi
import com.tarikyasar.curmin.data.remote.dto.CurrencyConversionDto
import com.tarikyasar.curmin.data.remote.dto.symbol.CurrencySymbolsDto
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

}