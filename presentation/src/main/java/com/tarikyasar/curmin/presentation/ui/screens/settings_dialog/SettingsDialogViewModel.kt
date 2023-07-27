package com.tarikyasar.curmin.presentation.ui.screens.settings_dialog

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.utils.manager.PreferenceManager
import com.tarikyasar.curmin.utils.manager.ThemeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsDialogViewModel @Inject constructor(
    private val themeManager: ThemeManager,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private var _state = mutableStateOf(SettingsDialogState())
    val state: State<SettingsDialogState> = _state

    init {
        getTheme()
        getAskToRemoveItemParameter()
    }

    fun setTheme(themes: Themes) {
        themeManager.setTheme(themes)
    }

    fun getTheme() {
        _state.value = _state.value.copy(themes = themeManager.getTheme())
    }

    fun getAskToRemoveItemParameter() {
        _state.value = _state.value.copy(
            askToRemoveItemParameter = preferenceManager.getPreference()
        )
    }

    fun setAskToRemoveItemParameter(askToRemoveItemParameter: Boolean) {
        preferenceManager.setPreference(askToRemoveItemParameter = askToRemoveItemParameter)
        _state.value = _state.value.copy(
            askToRemoveItemParameter = askToRemoveItemParameter
        )
    }
}