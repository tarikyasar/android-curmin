package com.tarikyasar.curmin.data.repository.currency.mapper

import com.tarikyasar.curmin.data.remote.dto.fluctuation.CurrencyFluctuationDto
import com.tarikyasar.curmin.domain.model.CurrencyFluctuation
import com.tarikyasar.curmin.utils.fromJson

fun CurrencyFluctuationDto.toCurrencyFluctuation(): CurrencyFluctuation {
    val currencyFluctuationString = this.rates
        .toString()
        .substring(5)
        .dropLast(1)

    return currencyFluctuationString.fromJson(CurrencyFluctuation::class.java)
}