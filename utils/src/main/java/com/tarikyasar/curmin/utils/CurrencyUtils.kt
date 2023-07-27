package com.tarikyasar.curmin.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object CurrencyUtils {
    private val priceDecimalFormat by lazy {
        val decimalFormatSymbols = DecimalFormatSymbols(Locale.getDefault())
        decimalFormatSymbols.decimalSeparator = decimalSeparator.single()
        decimalFormatSymbols.groupingSeparator = groupingSeparator.single()
        val pattern = "###,##0.00"
        DecimalFormat(pattern, decimalFormatSymbols).apply {
            isGroupingUsed = true
        }
    }

    fun formatCurrency(amount: Double): String {
        return priceDecimalFormat.format(amount)
    }

    const val decimalSeparator = ","
    const val groupingSeparator = "."
}