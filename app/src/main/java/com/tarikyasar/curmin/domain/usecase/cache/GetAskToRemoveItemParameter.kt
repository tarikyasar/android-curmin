package com.tarikyasar.curmin.domain.usecase.cache

import com.tarikyasar.curmin.domain.repository.SettingsRepository
import javax.inject.Inject

class GetAskToRemoveItemParameter @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke() = settingsRepository.getAskToRemoveItemParameter() ?: true
}
