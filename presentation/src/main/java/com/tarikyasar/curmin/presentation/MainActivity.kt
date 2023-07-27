package com.tarikyasar.curmin.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.tarikyasar.curmin.presentation.ui.utils.LoadingManager
import com.tarikyasar.curmin.presentation.ui.utils.PreferenceManager
import com.tarikyasar.curmin.presentation.ui.utils.ThemeManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var loadingManager: LoadingManager

    @Inject
    lateinit var themeManager: ThemeManager

    @Inject
    lateinit var preferenceManager: PreferenceManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurminApp(
                loadingManager = loadingManager,
                themeManager = themeManager,
                preferenceManager = preferenceManager
            )
        }
    }
}