package com.tarikyasar.curmin.presentation.ui.screens.settings_dialog

import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.presentation.ui.base.BaseViewModel
import com.tarikyasar.curmin.presentation.ui.screens.settings_dialog.SettingsDialogContract.Event
import com.tarikyasar.curmin.presentation.ui.screens.settings_dialog.SettingsDialogContract.Intent
import com.tarikyasar.curmin.presentation.ui.screens.settings_dialog.SettingsDialogContract.UiState
import com.tarikyasar.curmin.presentation.ui.utils.PreferenceManager
import com.tarikyasar.curmin.presentation.ui.utils.ThemeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsDialogViewModel @Inject constructor(
    private val themeManager: ThemeManager,
    private val preferenceManager: PreferenceManager
) : BaseViewModel<UiState, Intent, Event>(UiState()) {

    override fun onFirstLaunch() {
        super.onFirstLaunch()

        getTheme()
        getAskToRemoveItemParameter()
    }

    fun setTheme(themes: Themes) {
        themeManager.setTheme(themes)
    }

    fun getTheme() {
        updateUiState {
            copy(themes = themeManager.getTheme())
        }
    }

    fun getAskToRemoveItemParameter() {
        updateUiState {
            copy(
                askToRemoveItemParameter = preferenceManager.getPreference()
            )
        }
    }

    fun setAskToRemoveItemParameter(askToRemoveItemParameter: Boolean) {
        preferenceManager.setPreference(askToRemoveItemParameter = askToRemoveItemParameter)
        updateUiState {
            copy(
                askToRemoveItemParameter = askToRemoveItemParameter
            )
        }
    }

    override fun onIntent(intent: Intent) {
    }
}