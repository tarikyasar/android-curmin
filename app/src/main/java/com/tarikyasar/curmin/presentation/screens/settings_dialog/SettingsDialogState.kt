package com.tarikyasar.curmin.presentation.screens.settings_dialog

import com.tarikyasar.curmin.domain.model.Symbol
import com.tarikyasar.curmin.domain.model.Themes

data class SettingsDialogState(
    val themes: Themes? = null,
    val isLoading: Boolean = false,
    val baseCurrency: Symbol? = null,
    val currencySymbols: List<Symbol> = emptyList(),
    val error: String = "",
    val askToRemoveItemParameter: Boolean? = null
)