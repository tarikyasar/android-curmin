package com.tarikyasar.curmin.domain.usecase.cache

import com.tarikyasar.curmin.domain.model.Symbol
import com.tarikyasar.curmin.domain.repository.SettingsRepository
import javax.inject.Inject

class GetBaseCurrency @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke() =
        settingsRepository.getBaseCurrency() ?: Symbol("USD", "United States Dollar")
}
