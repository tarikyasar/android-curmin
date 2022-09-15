package com.tarikyasar.curmin.data.repository.currency.mapper

import com.tarikyasar.curmin.data.remote.dto.currency.CurrencyConversionDto
import com.tarikyasar.curmin.domain.model.ConvertedCurrency

fun CurrencyConversionDto.toConvertedCurrency(): ConvertedCurrency {
    return ConvertedCurrency(
        fromCurrencyCode = this.query.from,
        toCurrencyCode = this.query.to,
        amount = this.result,
        rate = this.info.rate
    )
}