package com.tarikyasar.curmin.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import com.tarikyasar.curmin.domain.model.Symbol
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.utils.manager.BaseCurrencyManager
import com.tarikyasar.curmin.utils.manager.ThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun rememberCurminAppState(
    themeManager: ThemeManager,
    baseCurrencyManager: BaseCurrencyManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(themeManager) {
    CurminAppState(
        themeManager = themeManager,
        baseCurrencyManager = baseCurrencyManager,
        coroutineScope = coroutineScope
    )
}

@Stable
class CurminAppState(
    val themeManager: ThemeManager,
    val baseCurrencyManager: BaseCurrencyManager,
    coroutineScope: CoroutineScope
) {
    init {
        coroutineScope.launch {
            themeManager.theme.collect {
                _themes = it
            }
        }

        coroutineScope.launch {
            baseCurrencyManager.currency.collect {
                _currency = it
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
}