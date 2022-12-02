package com.tarikyasar.curmin.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.tarikyasar.curmin.utils.manager.PreferenceManager
import com.tarikyasar.curmin.utils.manager.ThemeManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var themeManager: ThemeManager

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurminApp(
                themeManager = themeManager,
                preferenceManager = preferenceManager
            )
        }
    }
}