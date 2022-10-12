package com.tarikyasar.curmin.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T : Any> Gson.fromJson(json: String?): T? {
    return if (json.isNullOrEmpty()) {
        null
    } else {
        try {
            val type = object : TypeToken<T>() {}.type
            this.fromJson<T>(json, type)
        } catch (e: Exception) {
            Log.e("GSON", "Json parse error", e)
            null
        }
    }
}

fun String.insert(index: Int, char: Char): String {
    return toMutableList().apply { add(index, char) }.joinToString("")
}