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
        const val ARG_CURRENCY_DATE = "currencyDate"
        const val ARG_CURRENCY_RATE = "currencyRate"
        const val ROUTE =
            "currencyDetail/{$ARG_BASE_CURRENCY}/{$ARG_TARGET_CURRENCY}/{$ARG_CURRENCY_DATE}/{$ARG_CURRENCY_RATE}"
        val arguments: List<NamedNavArgument> = listOf(
            navArgument(ARG_BASE_CURRENCY) { type = NavType.StringType },
            navArgument(ARG_TARGET_CURRENCY) { type = NavType.StringType },
            navArgument(ARG_CURRENCY_DATE) { type = NavType.StringType },
            navArgument(ARG_CURRENCY_RATE) { type = NavType.StringType }
        )

        fun currencyDetailDestination(
            baseCurrency: String,
            targetCurrency: String,
            date: String,
            rate: String
        ): String {
            return "currencyDetail/$baseCurrency/$targetCurrency/$date/$rate"
        }
    }

}