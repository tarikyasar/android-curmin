package com.tarikyasar.curmin.data.repository.settings.datasource

import com.tarikyasar.curmin.domain.model.Themes

interface SettingsDataSource {
    interface Cache {
        fun setThemeParameters(themes: Themes?)

        fun getThemeParameters(): Themes?
    }
}
