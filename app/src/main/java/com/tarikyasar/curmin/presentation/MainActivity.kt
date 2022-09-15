package com.tarikyasar.curmin.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tarikyasar.curmin.R
import com.tarikyasar.curmin.presentation.composable.currency_watchlist_item.CurrencyWatchlistItem
import com.tarikyasar.curmin.presentation.settings_dialog.SettingsDialog
import com.tarikyasar.curmin.presentation.ui.theme.CurminTheme
import com.tarikyasar.curmin.utils.ThemeManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var themeManager: ThemeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CurminApp(themeManager = themeManager)
        }
    }
}