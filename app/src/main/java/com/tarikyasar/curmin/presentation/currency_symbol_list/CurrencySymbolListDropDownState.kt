package com.tarikyasar.curmin.presentation.currency_symbol_list

import com.tarikyasar.curmin.domain.model.Symbol

data class CurrencySymbolListDropDownState(
    val isLoading: Boolean = false,
    val currencySymbols: List<Symbol> = emptyList(),
    val error: String = ""
)