package com.tarikyasar.curmin.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.utils.ThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun rememberCurminAppState(
    themeManager: ThemeManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(themeManager) {
    CurminAppState(
        themeManager = themeManager,
        coroutineScope = coroutineScope
    )
}

@Stable
class CurminAppState(
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
}