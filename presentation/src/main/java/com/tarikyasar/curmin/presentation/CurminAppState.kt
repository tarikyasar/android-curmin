package com.tarikyasar.curmin.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.presentation.ui.utils.LoadingManager
import com.tarikyasar.curmin.presentation.ui.utils.PreferenceManager
import com.tarikyasar.curmin.presentation.ui.utils.ThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberCurminAppState(
    loadingManager: LoadingManager,
    themeManager: ThemeManager,
    preferenceManager: PreferenceManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(
    loadingManager,
    themeManager,
    preferenceManager
) {
    CurminAppState(
        loadingManager = loadingManager,
        themeManager = themeManager,
        preferenceManager = preferenceManager,
        coroutineScope = coroutineScope
    )
}

@Stable
class CurminAppState(
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
}