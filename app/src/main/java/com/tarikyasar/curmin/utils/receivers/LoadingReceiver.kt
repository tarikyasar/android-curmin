package com.tarikyasar.curmin.utils.receivers

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object LoadingReceiver {

    private val _sharedFlow = MutableSharedFlow<Boolean>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    suspend fun sendLoadingEvents(vararg events: Boolean) {
        coroutineScope {
            for (isLoading in events) {
                _sharedFlow.emit(isLoading)
            }
        }
    }
}