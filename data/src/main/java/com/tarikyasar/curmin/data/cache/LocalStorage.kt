package com.tarikyasar.curmin.data.cache

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.utils.GsonHolder
import com.tarikyasar.curmin.utils.fromJson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalStorage @Inject constructor(
    @ApplicationContext context: Context
) {
    private val preferences = getSharedPreferences(context)

    var themes: Themes?
        get() {
            val json = preferences.getString(PreferenceKey.SYSTEM_THEME.key, "")
            return GsonHolder.gson.fromJson(json)
        }
        set(value) {
            val json = GsonHolder.gson.toJson(value)
            preferences.edit {
                putString(PreferenceKey.SYSTEM_THEME.key, json)
            }
        }

    var askToRemoveItemParameter: Boolean?
        get() {
            val json = preferences.getString(PreferenceKey.ASK_REMOVE_ITEM.key, "")
            return GsonHolder.gson.fromJson(json)
        }
        set(value) {
            val json = GsonHolder.gson.toJson(value)
            preferences.edit {
                putString(PreferenceKey.ASK_REMOVE_ITEM.key, json)
            }
        }

    companion object {
        fun getSharedPreferences(context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
    }
}

enum class PreferenceKey(
    val key: String,
) {
    SYSTEM_THEME("SYSTEM_THEME"),
    ASK_REMOVE_ITEM("ASK_REMOVE_ITEM");

    override fun toString(): String = key
}
