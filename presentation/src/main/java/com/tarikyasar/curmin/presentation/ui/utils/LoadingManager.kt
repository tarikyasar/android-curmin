package com.tarikyasar.curmin.presentation.ui.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadingManager @Inject constructor() {

    private var _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun setLoading(isLoading: Boolean) {
        _loading.value = isLoading
    }

}