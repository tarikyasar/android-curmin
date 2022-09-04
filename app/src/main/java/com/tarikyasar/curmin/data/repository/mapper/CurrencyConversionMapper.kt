package com.tarikyasar.curmin.data.repository.mapper

import com.tarikyasar.curmin.data.remote.dto.CurrencyConversionDto
import com.tarikyasar.curmin.domain.model.ConvertedCurrency

fun CurrencyConversionDto.toConvertedCurrency(): ConvertedCurrency {
    return ConvertedCurrency(
        fromCurrencyCode = this.query.from,
        toCurrencyCode = this.query.to,
        amount = this.result,
        rate = this.info.rate
    )
}