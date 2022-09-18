package com.tarikyasar.curmin.data.repository.settings

import com.tarikyasar.curmin.data.repository.settings.datasource.SettingsDataSource
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsCacheDataSource: SettingsDataSource.Cache
) : SettingsRepository {

    override fun setThemeParameters(themes: Themes?) {
        settingsCacheDataSource.setThemeParameters(themes)
    }

    override fun getThemeParameters() = settingsCacheDataSource.getThemeParameters()
}