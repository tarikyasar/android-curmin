package com.tarikyasar.curmin.domain.usecase.cache

import com.tarikyasar.curmin.domain.repository.SettingsRepository
import javax.inject.Inject

class GetAskRemoveItem @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke() = settingsRepository.getAskRemoveItem() ?: true
}
