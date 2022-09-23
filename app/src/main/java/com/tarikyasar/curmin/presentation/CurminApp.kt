package com.tarikyasar.curmin.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.tarikyasar.curmin.common.Navigations
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.presentation.screens.currency_detail.CurrencyDetail
import com.tarikyasar.curmin.presentation.screens.currency_watchlist.CurrencyWatchlist
import com.tarikyasar.curmin.presentation.ui.theme.CurminTheme
import com.tarikyasar.curmin.utils.manager.ThemeManager


@OptIn(ExperimentalAnimationApi::class)
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

    val springSpec = spring<IntOffset>(dampingRatio = Spring.DampingRatioNoBouncy)
    val offSetX = 1000

    CurminTheme(darkTheme = appState.isDarkTheme) {
        AnimatedNavHost(
            navController = appState.navController,
            startDestination = appState.startDestination,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { offSetX }, animationSpec = springSpec)
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -offSetX }, animationSpec = springSpec)
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -offSetX }, animationSpec = springSpec)
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { offSetX }, animationSpec = springSpec)
            }
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
                    onNavigateBack = {
                        appState.navController.popBackStack()
                    },
                    baseCurrency = backStackEntry.arguments?.getString(Navigations.CurrencyDetailNavigation.ARG_BASE_CURRENCY),
                    targetCurrency = backStackEntry.arguments?.getString(Navigations.CurrencyDetailNavigation.ARG_TARGET_CURRENCY)
                )
            }
        }
    }
}