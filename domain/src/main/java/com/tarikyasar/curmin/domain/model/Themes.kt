package com.tarikyasar.curmin.domain.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.tarikyasar.curmin.domain.R

enum class Themes {
    LIGHT,
    DARK,
    SYSTEM_THEME;

    @Composable
    fun getThemeName(): String {
        return when (this) {
            LIGHT -> stringResource(id = R.string.theme_light)
            DARK -> stringResource(id = R.string.theme_dark)
            SYSTEM_THEME -> stringResource(id = R.string.theme_system)
        }
    }
}