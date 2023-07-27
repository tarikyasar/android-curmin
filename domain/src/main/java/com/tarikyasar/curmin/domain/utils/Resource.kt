package com.tarikyasar.curmin.domain.utils

import com.tarikyasar.curmin.domain.model.CurminError

sealed class Resource<T>(val data: T? = null, val error: CurminError? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(error: CurminError, data: T? = null) : Resource<T>(data, error)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}