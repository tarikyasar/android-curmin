package com.tarikyasar.curmin.presentation.ui.screens.settings_dialog

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
) : BaseViewModel<UiState, Intent, Event>(
    UiState(
        themes = themeManager.getTheme(),
        askToRemoveItemParameter = preferenceManager.getPreference()
    )
) {

    fun setAskToRemoveItemParameter(askToRemoveItemParameter: Boolean) {
        preferenceManager.setPreference(askToRemoveItemParameter = askToRemoveItemParameter)
        updateUiState {
            copy(
                askToRemoveItemParameter = askToRemoveItemParameter
            )
        }
    }

    override fun onIntent(intent: Intent) {
        when (intent) {
            Intent.GetTheme -> {
                updateUiState {
                    copy(themes = themeManager.getTheme())
                }
            }

            Intent.GetAskToRemoveItemParameter -> {
                updateUiState {
                    copy(askToRemoveItemParameter = preferenceManager.getPreference())
                }
            }

            is Intent.SetTheme -> {
                themeManager.setTheme(intent.themes)
            }

            is Intent.SetAskToRemoveItemParameter -> {
                preferenceManager.setPreference(intent.value)
            }
        }
    }
}