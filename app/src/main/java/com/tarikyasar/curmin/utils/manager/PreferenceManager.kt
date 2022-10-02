package com.tarikyasar.curmin.utils.manager

import com.tarikyasar.curmin.domain.usecase.cache.GetAskRemoveItem
import com.tarikyasar.curmin.domain.usecase.cache.SetAskRemoveItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(
    getAskRemoveItem: GetAskRemoveItem,
    private var setAskRemoveItem: SetAskRemoveItem
) {
    private var _preference = MutableStateFlow(getAskRemoveItem())
    val preference = _preference.asStateFlow()

    fun setPreference(askRemoveItem: Boolean) {
        setAskRemoveItem(askRemoveItem)
        _preference.value = askRemoveItem
    }

    fun getPreference() = _preference.value
}