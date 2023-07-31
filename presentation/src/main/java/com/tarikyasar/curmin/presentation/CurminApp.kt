package com.tarikyasar.curmin.presentation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.presentation.composable.LoadingAnimation
import com.tarikyasar.curmin.presentation.ui.screens.currency.NavGraphs
import com.tarikyasar.curmin.presentation.ui.screens.currency.destinations.CurrencyWatchlistDestination
import com.tarikyasar.curmin.presentation.ui.theme.CurminTheme
import com.tarikyasar.curmin.presentation.ui.utils.LoadingManager
import com.tarikyasar.curmin.presentation.ui.utils.PreferenceManager
import com.tarikyasar.curmin.presentation.ui.utils.ThemeManager

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@Composable
fun CurminApp(
    loadingManager: LoadingManager,
    themeManager: ThemeManager,
    preferenceManager: PreferenceManager
) {
    val appState = rememberCurminAppState(
        loadingManager = loadingManager,
        themeManager = themeManager,
        preferenceManager = preferenceManager
    )

    when (themeManager.getTheme()) {
        Themes.LIGHT -> setDefaultNightMode(MODE_NIGHT_NO)
        Themes.DARK -> setDefaultNightMode(MODE_NIGHT_YES)
        Themes.SYSTEM_THEME -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
    }

    CurminTheme(darkTheme = appState.isDarkTheme) {
        Box {
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                startRoute = CurrencyWatchlistDestination,
                navController = rememberNavController(),
                modifier = Modifier.blur(if (appState.showLoading) 50.dp else 0.dp)
            )

            if (appState.showLoading) {
                LoadingAnimation(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}