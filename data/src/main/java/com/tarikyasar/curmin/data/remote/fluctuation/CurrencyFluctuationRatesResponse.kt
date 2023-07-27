package com.tarikyasar.curmin.data.remote.fluctuation

import com.tarikyasar.curmin.domain.model.fluctuation.CurrencyFluctuation
import com.tarikyasar.curmin.utils.fromJson

data class CurrencyFluctuationRatesResponse(
    val rates: Any
)

fun CurrencyFluctuationRatesResponse.toCurrencyFluctuation(): CurrencyFluctuation {
    val currencyFluctuationString = this.rates
        .toString()
        .substring(5)
        .dropLast(1)

    return currencyFluctuationString.fromJson(CurrencyFluctuation::class.java)
}