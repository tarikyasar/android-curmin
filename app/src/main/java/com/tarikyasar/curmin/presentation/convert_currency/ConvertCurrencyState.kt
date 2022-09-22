package com.tarikyasar.curmin.presentation.convert_currency

import com.tarikyasar.curmin.domain.model.ConvertedCurrency

data class ConvertCurrencyState(
    val isLoading: Boolean = false,
    val convertedCurrency: ConvertedCurrency? = null,
    val error: String = ""
)