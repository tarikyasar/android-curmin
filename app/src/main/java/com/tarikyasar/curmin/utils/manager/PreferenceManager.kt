package com.tarikyasar.curmin.utils.manager

import com.tarikyasar.curmin.domain.usecase.cache.GetAskToRemoveItemParameter
import com.tarikyasar.curmin.domain.usecase.cache.SetAskToRemoveItemParameter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(
    getAskToRemoveItemParameter: GetAskToRemoveItemParameter,
    private var setAskToRemoveItemParameter: SetAskToRemoveItemParameter
) {
    private var _preference = MutableStateFlow(getAskToRemoveItemParameter())
    val preference = _preference.asStateFlow()

    fun setPreference(askToRemoveItemParameter: Boolean) {
        setAskToRemoveItemParameter(askToRemoveItemParameter)
        _preference.value = askToRemoveItemParameter
    }

    fun getPreference() = _preference.value
}