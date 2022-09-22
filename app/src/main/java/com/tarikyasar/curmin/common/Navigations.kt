package com.tarikyasar.curmin.common

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

object Navigations {

    object CurrencyWatchlistNavigation {
        const val ROUTE = "currencyWatchlist"
    }

    object CurrencyDetailNavigation {
        const val ARG_BASE_CURRENCY = "baseCurrency"
        const val ARG_TARGET_CURRENCY = "targetCurrency"
        const val ROUTE = "currencyDetail/{$ARG_BASE_CURRENCY}/{$ARG_TARGET_CURRENCY}"
        val arguments: List<NamedNavArgument> = listOf(
            navArgument(ARG_BASE_CURRENCY) { type = NavType.StringType },
            navArgument(ARG_TARGET_CURRENCY) { type = NavType.StringType }
        )

        fun currencyDetailDestination(baseCurrency: String, targetCurrency: String): String {
            return "currencyDetail/$baseCurrency/$targetCurrency"
        }
    }

}