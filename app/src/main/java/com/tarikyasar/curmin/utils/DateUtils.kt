package com.tarikyasar.curmin.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTime(time: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy")
        return time.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTime(timeInMs: Long): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(timeInMs)
    }
}