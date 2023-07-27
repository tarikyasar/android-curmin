package com.tarikyasar.curmin.data.remote.fluctuation

import com.tarikyasar.curmin.domain.model.fluctuation.CurrencyFluctuationRates

data class CurrencyFluctuationRatesResponse(
    val rates: Any
)

fun CurrencyFluctuationRatesResponse.toDomain(): CurrencyFluctuationRates {
    return CurrencyFluctuationRates(
        rates = rates
    )
}