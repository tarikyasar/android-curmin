package com.tarikyasar.curmin.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tarikyasar.curmin.common.Navigations
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.presentation.screens.currency_detail.CurrencyDetail
import com.tarikyasar.curmin.presentation.screens.currency_watchlist.CurrencyWatchlist
import com.tarikyasar.curmin.presentation.ui.theme.CurminTheme
import com.tarikyasar.curmin.utils.manager.ThemeManager


@SuppressLint("UnrememberedMutableState")
@Composable
fun CurminApp(
    themeManager: ThemeManager,
) {
    val appState = rememberCurminAppState(
        themeManager = themeManager,
    )

    when (themeManager.getTheme()) {
        Themes.LIGHT -> setDefaultNightMode(MODE_NIGHT_NO)
        Themes.DARK -> setDefaultNightMode(MODE_NIGHT_YES)
        Themes.SYSTEM_THEME -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
    }

    CurminTheme(darkTheme = appState.isDarkTheme) {
        NavHost(
            navController = appState.navController,
            startDestination = appState.startDestination
        ) {
            composable(
                Navigations.CurrencyWatchlistNavigation.ROUTE,
                arguments = Navigations.CurrencyDetailNavigation.arguments
            ) {
                CurrencyWatchlist(
                    onNavigateToCurrencyDetail = { baseCurrency, targetCurrency ->
                        appState.navController.navigate(
                            Navigations.CurrencyDetailNavigation.currencyDetailDestination(
                                baseCurrency = baseCurrency,
                                targetCurrency = targetCurrency
                            )
                        )
                    }
                )
            }

            composable(Navigations.CurrencyDetailNavigation.ROUTE) { backStackEntry ->
                CurrencyDetail(
                    onNavigate = {
                        appState.navController.popBackStack()
                    },
                    baseCurrency = backStackEntry.arguments?.getString(Navigations.CurrencyDetailNavigation.ARG_BASE_CURRENCY),
                    targetCurrency = backStackEntry.arguments?.getString(Navigations.CurrencyDetailNavigation.ARG_TARGET_CURRENCY)
                )
            }
        }
    }
}