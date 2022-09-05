package com.tarikyasar.curmin.data.repository.mapper

import com.tarikyasar.curmin.data.remote.dto.symbol.CurrencySymbolsDto
import com.tarikyasar.curmin.domain.model.Symbol

fun CurrencySymbolsDto.toCurrencySymbol(): List<Symbol> {
    val symbolList = mutableListOf<Symbol>()

    this.symbols
        .toString()
        .split("{")[1]
        .dropLast(2)
        .split(",")
        .forEach { symbol ->
            val splittedSymbol = symbol
                .trim()
                .split("=")

            symbolList.add(
                Symbol(
                    code = splittedSymbol[0].trim(),
                    name = splittedSymbol[1].trim()
                )
            )
        }

    return symbolList
}