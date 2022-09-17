package com.tarikyasar.curmin.utils.manager

import com.tarikyasar.curmin.domain.model.Symbol
import com.tarikyasar.curmin.domain.usecase.cache.GetBaseCurrency
import com.tarikyasar.curmin.domain.usecase.cache.SetBaseCurrency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseCurrencyManager @Inject constructor(
    getBaseCurrency: GetBaseCurrency,
    private var setBaseCurrency: SetBaseCurrency
) {
    private var _currency = MutableStateFlow(getBaseCurrency())
    val currency = _currency.asStateFlow()

    fun setCurrency(currency: Symbol) {
        setBaseCurrency(currency)
        _currency.value = currency
    }

    fun getCurrency() = _currency.value
}