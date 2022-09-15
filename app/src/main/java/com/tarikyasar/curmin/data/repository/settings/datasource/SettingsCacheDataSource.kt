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

    override fun getThemeParameters(): Themes? {
        return localStorage.themes
    }
}
