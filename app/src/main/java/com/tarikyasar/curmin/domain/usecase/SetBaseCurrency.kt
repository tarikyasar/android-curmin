package com.tarikyasar.curmin.domain.usecase

import com.tarikyasar.curmin.domain.model.Symbol
import com.tarikyasar.curmin.domain.repository.SettingsRepository
import javax.inject.Inject

class SetBaseCurrency @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(params: Symbol?) = settingsRepository.setBaseCurrency(params)
}
