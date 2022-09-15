package com.tarikyasar.curmin.domain.model

enum class Themes {
    LIGHT,
    DARK,
    SYSTEM_THEME;

    fun getThemeName(): String {
        return when (this) {
            LIGHT -> "Light"
            DARK -> "Dark"
            SYSTEM_THEME -> "System Theme"
        }
    }
}