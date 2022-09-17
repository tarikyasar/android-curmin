package com.tarikyasar.curmin.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.presentation.screens.currency_watchlist.CurrencyWatchlist
import com.tarikyasar.curmin.presentation.ui.theme.CurminTheme
import com.tarikyasar.curmin.utils.manager.BaseCurrencyManager
import com.tarikyasar.curmin.utils.manager.ThemeManager

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CurminApp(
    themeManager: ThemeManager,
    baseCurrencyManager: BaseCurrencyManager
) {
    val appState = rememberCurminAppState(
        themeManager = themeManager,
        baseCurrencyManager = baseCurrencyManager
    )

    when (themeManager.getTheme()) {
        Themes.LIGHT -> setDefaultNightMode(MODE_NIGHT_NO)
        Themes.DARK -> setDefaultNightMode(MODE_NIGHT_YES)
        Themes.SYSTEM_THEME -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
    }

    CurminTheme(darkTheme = appState.isDarkTheme) {
        CurrencyWatchlist()
    }
}