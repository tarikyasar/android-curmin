package com.tarikyasar.curmin.utils

import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.domain.usecase.GetThemeParameters
import com.tarikyasar.curmin.domain.usecase.SetThemeParameters
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManager @Inject constructor(
    getThemeParameters: GetThemeParameters,
    private var setThemeParameters: SetThemeParameters
) {
    private var _theme = MutableStateFlow(getThemeParameters())
    val theme = _theme.asStateFlow()

    fun setTheme(theme: Themes) {
        setThemeParameters(theme)
        _theme.value = theme
    }

    fun getTheme() = _theme.value
}