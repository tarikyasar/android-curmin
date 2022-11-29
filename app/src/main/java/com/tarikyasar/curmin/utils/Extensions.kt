package com.tarikyasar.curmin.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/* JSON Extensions */
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

fun <A> A.toJson(): String? {
    return Gson().toJson(this)
}

fun <A> String.fromJson(type: Class<A>): A {
    return Gson().fromJson(this, type)
}

/* String Extensions */
fun String.insert(index: Int, char: Char): String {
    return toMutableList().apply { add(index, char) }.joinToString("")
}

fun String.formatDate(): Pair<String, String> {
    val splittedString = this.split(" ")

    return Pair(splittedString.first(), splittedString.last())
}