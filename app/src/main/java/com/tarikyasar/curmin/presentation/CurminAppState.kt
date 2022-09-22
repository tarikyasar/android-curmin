package com.tarikyasar.curmin.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.tarikyasar.curmin.common.Navigations
import com.tarikyasar.curmin.domain.model.Symbol
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.utils.manager.ThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberCurminAppState(
    navController: NavHostController = rememberAnimatedNavController(),
    themeManager: ThemeManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(
    navController,
    themeManager
) {
    CurminAppState(
        navController = navController,
        themeManager = themeManager,
        coroutineScope = coroutineScope
    )
}

@Stable
class CurminAppState(
    val navController: NavHostController,
    val themeManager: ThemeManager,
    coroutineScope: CoroutineScope
) {
    init {
        coroutineScope.launch {
            themeManager.theme.collect {
                _themes = it
            }
        }
    }

    // Theme State
    private var _themes by mutableStateOf(Themes.SYSTEM_THEME)
    val isDarkTheme: Boolean
        @Composable get() = when (_themes) {
            Themes.SYSTEM_THEME -> isSystemInDarkTheme()
            Themes.DARK -> true
            Themes.LIGHT -> false
        }

    // Base Currency State
    private var _currency by mutableStateOf(Symbol("USD", "United States Dollar"))
    val currency: Symbol
        @Composable get() = _currency
    
    
    // Navigation
    val startDestination = Navigations.CurrencyWatchlistNavigation.ROUTE
}