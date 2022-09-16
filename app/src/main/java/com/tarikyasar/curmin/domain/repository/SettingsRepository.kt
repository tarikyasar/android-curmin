package com.tarikyasar.curmin.domain.repository

import com.tarikyasar.curmin.domain.model.Symbol
import com.tarikyasar.curmin.domain.model.Themes

interface SettingsRepository {

    fun setThemeParameters(themes: Themes?)

    fun getThemeParameters(): Themes?

    fun setBaseCurrency(currency: Symbol?)

    fun getBaseCurrency(): Symbol?
}