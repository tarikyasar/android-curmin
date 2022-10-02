package com.tarikyasar.curmin.domain.repository

import com.tarikyasar.curmin.domain.model.Themes

interface SettingsRepository {

    fun setThemeParameters(themes: Themes?)

    fun setAskRemoveItem(askRemoveItem: Boolean?)

    fun getThemeParameters(): Themes?

    fun getAskRemoveItem(): Boolean?
}