package com.tarikyasar.curmin.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonHolder {
    val gson: Gson by lazy {
        GsonBuilder()
            .create()
    }
}
