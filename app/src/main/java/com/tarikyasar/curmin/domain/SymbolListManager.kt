package com.tarikyasar.curmin.domain

import com.tarikyasar.curmin.domain.model.Symbol

object SymbolListManager {
    var symbols: List<Symbol> = listOf(
        Symbol(
            code = "TRY",
            name = "Turkish Lira"
        ),
        Symbol(
            code = "USD",
            name = "United States Dollar"
        ),
        Symbol(
            code = "EUR",
            name = "Euro"
        )
    )
}