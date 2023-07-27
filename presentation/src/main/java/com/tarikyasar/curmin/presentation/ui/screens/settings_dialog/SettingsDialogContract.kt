package com.tarikyasar.curmin.presentation.ui.screens.settings_dialog

import com.tarikyasar.curmin.domain.model.Symbol
import com.tarikyasar.curmin.domain.model.Themes

interface SettingsDialogContract {

    data class UiState(
        val themes: Themes? = null,
        val baseCurrency: Symbol? = null,
        val currencySymbols: List<Symbol> = emptyList(),
        val error: String = "",
        val askToRemoveItemParameter: Boolean? = null
    )

    sealed class Intent {
        object GetTheme : Intent()

        object GetAskToRemoveItemParameter : Intent()

        data class SetTheme(val themes: Themes) : Intent()

        data class SetAskToRemoveItemParameter(val value: Boolean) : Intent()
    }

    sealed class Event
}