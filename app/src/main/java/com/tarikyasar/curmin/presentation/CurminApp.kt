package com.tarikyasar.curmin.presentation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.tarikyasar.curmin.common.Navigations
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.presentation.screens.currency_detail.CurrencyDetail
import com.tarikyasar.curmin.presentation.screens.currency_watchlist.CurrencyWatchlist
import com.tarikyasar.curmin.presentation.ui.theme.CurminTheme
import com.tarikyasar.curmin.utils.fromJson
import com.tarikyasar.curmin.utils.manager.PreferenceManager
import com.tarikyasar.curmin.utils.manager.ThemeManager
import com.tarikyasar.curmin.utils.toJson

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun CurminApp(
    themeManager: ThemeManager,
    preferenceManager: PreferenceManager
) {
    val appState = rememberCurminAppState(
        themeManager = themeManager,
        preferenceManager = preferenceManager
    )

    when (themeManager.getTheme()) {
        Themes.LIGHT -> setDefaultNightMode(MODE_NIGHT_NO)
        Themes.DARK -> setDefaultNightMode(MODE_NIGHT_YES)
        Themes.SYSTEM_THEME -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
    }

    val springSpec = spring<IntOffset>(dampingRatio = Spring.DampingRatioNoBouncy)
    val offSetX = 1000

    CurminTheme(darkTheme = appState.isDarkTheme) {
        Box {
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
                },
                modifier = Modifier.align(Alignment.Center)
            ) {
                composable(
                    Navigations.CurrencyWatchlistNavigation.ROUTE,
                    arguments = Navigations.CurrencyDetailNavigation.arguments
                ) {
                    CurrencyWatchlist(
                        onNavigateToCurrencyDetail = { currency ->
                            appState.navController.navigate(
                                Navigations.CurrencyDetailNavigation.currencyDetailDestination(
                                    currency.toJson() ?: ""
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
                        currency = backStackEntry.arguments?.getString(Navigations.CurrencyDetailNavigation.ARG_CURRENCY)
                            ?.fromJson(CurrencyWatchlistItemData::class.java)
                    )
                }
            }
        }
    }
}