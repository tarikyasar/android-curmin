package com.tarikyasar.curmin.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tarikyasar.curmin.R
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.presentation.screens.currency_watchlist.CurrencyWatchlist
import com.tarikyasar.curmin.presentation.screens.settings_dialog.SettingsDialog
import com.tarikyasar.curmin.presentation.ui.theme.CurminTheme
import com.tarikyasar.curmin.utils.manager.ThemeManager


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
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

    var showSettingsDialog by remember { mutableStateOf(false) }

    CurminTheme(darkTheme = appState.isDarkTheme) {
        Box(
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                showSettingsDialog = true
                            }
                            .size(34.dp)
                    ) {
                        if (showSettingsDialog) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .clip(CircleShape)
                                    .size(34.dp)
                                    .background(MaterialTheme.colors.primary)
                            )
                        }

                        Icon(
                            painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = null,
                            tint = MaterialTheme.colors.onBackground,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .background(
                                    MaterialTheme.colors.background,
                                    CircleShape
                                )
                                .size(32.dp)
                        )
                    }

                }

                CurrencyWatchlist()
            }

            SettingsDialog(
                openSettingsDialog = showSettingsDialog,
                onDismissRequest = { showSettingsDialog = false }
            )
        }
    }
}