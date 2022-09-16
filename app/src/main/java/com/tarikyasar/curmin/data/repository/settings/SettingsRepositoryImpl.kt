package com.tarikyasar.curmin.data.repository.settings

import com.tarikyasar.curmin.data.repository.settings.datasource.SettingsDataSource
import com.tarikyasar.curmin.domain.model.Currency
import com.tarikyasar.curmin.domain.model.Symbol
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsCacheDataSource: SettingsDataSource.Cache
) : SettingsRepository {

    override fun setThemeParameters(themes: Themes?) {
        settingsCacheDataSource.setThemeParameters(themes)
    }

    override fun setBaseCurrency(currency: Symbol?) {
        settingsCacheDataSource.setBaseCurrency(currency)
    }

    override fun getThemeParameters() = settingsCacheDataSource.getThemeParameters()

    override fun getBaseCurrency() = settingsCacheDataSource.getBaseCurrency()
}