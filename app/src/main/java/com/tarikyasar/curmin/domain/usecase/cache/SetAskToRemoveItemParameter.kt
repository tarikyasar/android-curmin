package com.tarikyasar.curmin.domain.usecase.cache

import com.tarikyasar.curmin.domain.repository.SettingsRepository
import javax.inject.Inject

class SetAskToRemoveItemParameter @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(params: Boolean?) = settingsRepository.setAskToRemoveItemParameter(params)
}
