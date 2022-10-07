package com.tarikyasar.curmin.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTime(time: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("hh:mm:ss D.M.y")
        return time.format(formatter)
    }
}