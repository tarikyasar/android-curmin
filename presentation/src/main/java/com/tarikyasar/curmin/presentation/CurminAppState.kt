package com.tarikyasar.curmin.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.presentation.ui.navigation.Navigations
import com.tarikyasar.curmin.presentation.ui.utils.LoadingManager
import com.tarikyasar.curmin.presentation.ui.utils.PreferenceManager
import com.tarikyasar.curmin.presentation.ui.utils.ThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberCurminAppState(
    navController: NavHostController = rememberAnimatedNavController(),
    loadingManager: LoadingManager,
    themeManager: ThemeManager,
    preferenceManager: PreferenceManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(
    navController,
    loadingManager,
    themeManager,
    preferenceManager
) {
    CurminAppState(
        navController = navController,
        loadingManager = loadingManager,
        themeManager = themeManager,
        preferenceManager = preferenceManager,
        coroutineScope = coroutineScope
    )
}

@Stable
class CurminAppState(
    val navController: NavHostController,
    val loadingManager: LoadingManager,
    val themeManager: ThemeManager,
    val preferenceManager: PreferenceManager,
    coroutineScope: CoroutineScope
) {
    init {
        coroutineScope.launch {
            loadingManager.loading.collect {
                _showLoading = it
            }
        }
        coroutineScope.launch {
            themeManager.theme.collect {
                _themes = it
            }
        }
        coroutineScope.launch {
            preferenceManager.preference.collect {
                _preference = it
            }
        }
    }

    // Loading state
    private var _showLoading by mutableStateOf(false)
    val showLoading: Boolean
        @Composable get() = _showLoading

    // Theme State
    private var _themes by mutableStateOf(Themes.SYSTEM_THEME)
    val isDarkTheme: Boolean
        @Composable get() = when (_themes) {
            Themes.SYSTEM_THEME -> isSystemInDarkTheme()
            Themes.DARK -> true
            Themes.LIGHT -> false
        }

    // Preference State
    private var _preference by mutableStateOf(true)
    val askToRemoveItemParameter: Boolean
        @Composable get() = _preference

    // Navigation
    val startDestination = Navigations.CurrencyWatchlistNavigation.ROUTE
}