package com.tarikyasar.curmin.domain.usecase

import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.domain.repository.SettingsRepository
import javax.inject.Inject

class SetThemeParameters @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(params: Themes?) = settingsRepository.setThemeParameters(params)
}
