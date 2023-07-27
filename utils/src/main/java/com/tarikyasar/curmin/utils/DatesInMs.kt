package com.tarikyasar.curmin.utils

enum class DatesInMs(val value: Long) {
    HOUR(60*60*1000),
    DAY(24* HOUR.value),
    WEEK(7* DAY.value),
    MONTH(30* DAY.value),
    YEAR(12* MONTH.value);
}