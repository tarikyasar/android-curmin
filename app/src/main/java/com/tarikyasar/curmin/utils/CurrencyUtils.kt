package com.tarikyasar.curmin.utils

object CurrencyUtils {
    fun formatCurrency(amount: Double): String {
        return String.format("%.2f", amount)
    }
}