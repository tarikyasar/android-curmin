package com.tarikyasar.curmin.presentation.screens.settings_dialog

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.domain.model.Symbol
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.domain.usecase.GetCurrencySymbolsUseCase
import com.tarikyasar.curmin.utils.manager.BaseCurrencyManager
import com.tarikyasar.curmin.utils.manager.ThemeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SettingsDialogViewModel @Inject constructor(
    private val themeManager: ThemeManager,
    private val baseCurrencyManager: BaseCurrencyManager,
    private val getCurrencySymbolsUseCase: GetCurrencySymbolsUseCase
) : ViewModel() {

    private var _state = mutableStateOf(SettingsDialogState())
    val state: State<SettingsDialogState> = _state

    init {
        getTheme()
        getBaseCurrency()
    }

    fun setTheme(themes: Themes) {
        themeManager.setTheme(themes)
    }

    fun getTheme() {
        val theme = themeManager.getTheme()
        _state.value = _state.value.copy(themes = theme)
    }

    fun setBaseCurrency(currency: Symbol) {
        baseCurrencyManager.setCurrency(currency)
    }

    fun getBaseCurrency() {
        val currency = baseCurrencyManager.getCurrency()
        _state.value = _state.value.copy(baseCurrency = currency)
    }

    private fun getSymbols() {
        getCurrencySymbolsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> _state.value = _state.value.copy(currencySymbols = result.data ?: emptyList(), error = "", isLoading = false)
                is Resource.Error -> _state.value = _state.value.copy(currencySymbols = emptyList(), error = "An unexpected error occured.", isLoading = false)
                is Resource.Loading -> _state.value = _state.value.copy(currencySymbols = emptyList(), error = "", isLoading = true)
            }
        }.launchIn(viewModelScope)
    }
}