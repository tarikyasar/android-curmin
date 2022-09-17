package com.tarikyasar.curmin.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.tarikyasar.curmin.data.database.AppDatabase
import com.tarikyasar.curmin.utils.manager.BaseCurrencyManager
import com.tarikyasar.curmin.utils.manager.ThemeManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var themeManager: ThemeManager

    @Inject
    lateinit var baseCurrencyManager: BaseCurrencyManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurminApp(
                themeManager = themeManager,
                baseCurrencyManager = baseCurrencyManager,
            )
        }
    }
}