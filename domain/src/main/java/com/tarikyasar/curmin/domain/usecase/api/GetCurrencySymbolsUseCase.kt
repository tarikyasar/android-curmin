package com.tarikyasar.curmin.domain.usecase.api

import com.tarikyasar.curmin.domain.SymbolListManager
import com.tarikyasar.curmin.domain.model.Symbol
import com.tarikyasar.curmin.domain.model.symbol.CurrencySymbols
import com.tarikyasar.curmin.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrencySymbolsUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {

    suspend operator fun invoke(): List<Symbol> {
        return SymbolListManager.symbols.ifEmpty {
            SymbolListManager.symbols = repository.getCurrencySymbols().toCurrencySymbol()
            return SymbolListManager.symbols
        }
    }
}

fun CurrencySymbols.toCurrencySymbol(): List<Symbol> {
    val symbolList = mutableListOf<Symbol>()

    this.symbols
        .toString()
        .split("{")[1]
        .dropLast(2)
        .split(",")
        .forEach { symbol ->
            val parsedSymbol = symbol
                .trim()
                .split("=")

            symbolList.add(
                Symbol(
                    code = parsedSymbol[0].trim(),
                    name = parsedSymbol[1].trim()
                )
            )
        }

    return symbolList
}