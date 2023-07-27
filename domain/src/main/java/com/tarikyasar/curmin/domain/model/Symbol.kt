package com.tarikyasar.curmin.domain.model

data class Symbol(
    val code: String,
    val name: String
) {
    fun hasText(text: String): Boolean {
        return (code.lowercase().contains(text.lowercase()) || name.lowercase().contains(text.lowercase()))
    }
}
