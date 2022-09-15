package com.tarikyasar.curmin.domain.usecase

import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.domain.repository.SettingsRepository
import javax.inject.Inject

class GetThemeParameters @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke() = settingsRepository.getThemeParameters() ?: Themes.SYSTEM_THEME
}
