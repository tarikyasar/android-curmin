package com.tarikyasar.curmin.common

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

object Navigations {

    object CurrencyWatchlistNavigation {
        const val ROUTE = "currencyWatchlist"
    }

    object CurrencyDetailNavigation {
        const val ARG_CURRENCY = "currency"
        const val ROUTE =
            "currencyDetail/{$ARG_CURRENCY}"
        val arguments: List<NamedNavArgument> = listOf(
            navArgument(ARG_CURRENCY) { type = NavType.StringType },
        )

        fun currencyDetailDestination(
            currency: String
        ): String {
            return "currencyDetail/$currency"
        }
    }
}