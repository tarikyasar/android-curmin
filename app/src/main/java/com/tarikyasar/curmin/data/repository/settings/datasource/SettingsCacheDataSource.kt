package com.tarikyasar.curmin.data.repository.settings.datasource

import com.tarikyasar.curmin.data.cache.LocalStorage
import com.tarikyasar.curmin.domain.model.Themes
import javax.inject.Inject

class SettingsCacheDataSource @Inject constructor(
    private val localStorage: LocalStorage
) : SettingsDataSource.Cache {

    override fun setThemeParameters(themes: Themes?) {
        localStorage.themes = themes
    }

    override fun setAskRemoveItem(askRemoveItem: Boolean?) {
        localStorage.askRemoveItem = askRemoveItem
    }

    override fun getThemeParameters(): Themes? {
        return localStorage.themes
    }

    override fun getAskRemoveItem(): Boolean? {
        return localStorage.askRemoveItem
    }
}
