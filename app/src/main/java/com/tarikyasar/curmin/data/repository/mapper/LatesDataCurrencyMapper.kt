package com.tarikyasar.curmin.data.repository.mapper

import com.tarikyasar.curmin.data.remote.dto.latest.LatestDataDto
import com.tarikyasar.curmin.domain.model.Currency
import com.tarikyasar.curmin.domain.model.LatestData

fun LatestDataDto.toCurrency(): LatestData {
    val currencies = mutableListOf<Currency>()

    this.rates
        .toString()
        .split("{")[1]
        .dropLast(1)
        .split(",")
        .forEach { currency ->

            val parsedCurrency = currency
                .split("=")

            currencies.add(
                Currency(
                    currencyCode = parsedCurrency[0].trim(),
                    currencyRate = parsedCurrency[1].trim().toDouble()
                )
            )
        }

    return LatestData(
        base = this.base,
        currencies = currencies
    )
}