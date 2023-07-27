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

    sealed class Intent

    sealed class Event
}