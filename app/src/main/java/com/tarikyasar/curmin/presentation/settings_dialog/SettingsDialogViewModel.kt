package com.tarikyasar.curmin.presentation.settings_dialog

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.utils.ThemeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsDialogViewModel @Inject constructor(
    private val themeManager: ThemeManager
) : ViewModel() {

    private val _state = mutableStateOf(SettingsDialogState())
    val state: State<SettingsDialogState> = _state

    init {
        getTheme()
    }

    fun setTheme(themes: Themes) {
        themeManager.setTheme(themes)
    }

    fun getTheme() {
        val theme = themeManager.getTheme()
        _state.value = SettingsDialogState(themes = theme)
    }
}