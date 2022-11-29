package com.tarikyasar.curmin.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.tarikyasar.curmin.common.Navigations
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.utils.manager.PreferenceManager
import com.tarikyasar.curmin.utils.manager.ThemeManager
import com.tarikyasar.curmin.utils.receivers.LoadingReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberCurminAppState(
    navController: NavHostController = rememberAnimatedNavController(),
    themeManager: ThemeManager,
    preferenceManager: PreferenceManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(
    navController,
    themeManager,
    preferenceManager
) {
    CurminAppState(
        navController = navController,
        themeManager = themeManager,
        preferenceManager = preferenceManager,
        coroutineScope = coroutineScope
    )
}

@Stable
class CurminAppState(
    val navController: NavHostController,
    val themeManager: ThemeManager,
    val preferenceManager: PreferenceManager,
    coroutineScope: CoroutineScope
) {
    init {
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

        coroutineScope.launch {
            LoadingReceiver.sharedFlow.collectLatest {
                println(it)
                _isLoading = it
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

    // Preference State
    private var _preference by mutableStateOf(true)
    val askToRemoveItemParameter: Boolean
        @Composable get() = _preference

    // Navigation
    val startDestination = Navigations.CurrencyWatchlistNavigation.ROUTE

    // Loading
    private var _isLoading by mutableStateOf(false)
    val isLoading: Boolean
        @Composable get() = _isLoading
}