package com.tarikyasar.curmin.presentation

import androidx.appcompat.app.AppCompatDelegate.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tarikyasar.curmin.R
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.presentation.composable.currency_watchlist_item.CurrencyWatchlistItem
import com.tarikyasar.curmin.presentation.settings_dialog.SettingsDialog
import com.tarikyasar.curmin.presentation.ui.theme.CurminTheme
import com.tarikyasar.curmin.utils.BaseCurrencyManager
import com.tarikyasar.curmin.utils.ThemeManager
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun CurminApp(
    themeManager: ThemeManager,
    baseCurrencyManager: BaseCurrencyManager
) {
    val appState = rememberCurminAppState(themeManager = themeManager, baseCurrencyManager = baseCurrencyManager)

    when (themeManager.getTheme()) {
        Themes.LIGHT -> setDefaultNightMode(MODE_NIGHT_NO)
        Themes.DARK -> setDefaultNightMode(MODE_NIGHT_YES)
        Themes.SYSTEM_THEME -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
    }

    CurminTheme(darkTheme = appState.isDarkTheme) {
        var changeValue1 by remember { mutableStateOf(Random.nextDouble(-0.25, 0.25)) }
        var changeValue2 by remember { mutableStateOf(Random.nextDouble(-0.25, 0.25)) }
        var showSettingsDialog by remember { mutableStateOf(false) }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
        ) {
            Box {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = null,
                            tint = MaterialTheme.colors.onBackground,
                            modifier = Modifier
                                .size(32.dp)
                                .clickable {
                                    showSettingsDialog = true
                                }
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        CurrencyWatchlistItem(
                            base = "USD",
                            target = "TRY",
                            value = 10.0,
                            change = (changeValue1 * 100.0).roundToInt() / 100.0,
                            date = "14.09.2022"
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        CurrencyWatchlistItem(
                            base = "USD",
                            target = "EUR",
                            value = 0.99,
                            change = (changeValue2 * 100.0).roundToInt() / 100.0,
                            date = "14.09.2022"
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                changeValue1 = Random.nextDouble(-0.25, 0.25)
                                changeValue2 = Random.nextDouble(-0.25, 0.25)
                            },
                            content = {
                                Text("Randomize")
                            }
                        )
                    }
                }

                SettingsDialog(
                    openSettingsDialog = showSettingsDialog,
                    onDismissRequest = { showSettingsDialog = false }
                )
            }
        }
    }
}