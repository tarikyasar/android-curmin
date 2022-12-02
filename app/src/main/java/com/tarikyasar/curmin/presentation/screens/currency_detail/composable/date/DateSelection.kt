package com.tarikyasar.curmin.presentation.screens.currency_detail.composable.date

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.material.datepicker.MaterialDatePicker
import com.tarikyasar.curmin.utils.DateUtils

enum class DateSelection {
    LAST_WEEK,
    LAST_MONTH,
    LAST_THREE_MONTHS,
    CUSTOM_DATE;

    fun getDateSelectionName(): String {
        return when (this) {
            LAST_WEEK -> "Last Week"
            LAST_MONTH -> "Last Month"
            LAST_THREE_MONTHS -> "Last Three Months"
            CUSTOM_DATE -> "Custom Date"
        }
    }

    fun getDateNames(): String {
        return when(this) {
            LAST_WEEK -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()
                val weekInMs = (7.0 * 24 * 60 * 60 * 1000).toLong()

                "${DateUtils.formatTimeWithDay(today - weekInMs)} - ${DateUtils.formatTimeWithDay(today)}"
            }
            LAST_MONTH -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()
                val monthInMs = (30.0 * 24 * 60 * 60 * 1000).toLong()

                "${DateUtils.formatTimeWithDay(today - monthInMs)} - ${DateUtils.formatTimeWithDay(today)}"
            }
            LAST_THREE_MONTHS -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()
                val threeMonthsInMs = (3 * 30.0 * 24 * 60 * 60 * 1000).toLong()

                "${DateUtils.formatTimeWithDay(today - threeMonthsInMs)} - ${DateUtils.formatTimeWithDay(today)}"
            }
            CUSTOM_DATE -> {
                ""
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateRanges(): Pair<String, String> {
        return when (this) {
            LAST_WEEK -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()
                val weekInMs = (7.0 * 24 * 60 * 60 * 1000).toLong()

                Pair(DateUtils.formatTime(today - weekInMs), DateUtils.formatTime(today))
            }
            LAST_MONTH -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()
                val monthInMs = (30.0 * 24 * 60 * 60 * 1000).toLong()

                Pair(DateUtils.formatTime(today - monthInMs), DateUtils.formatTime(today))
            }
            LAST_THREE_MONTHS -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()
                val threeMonthsInMs = (3 * 30.0 * 24 * 60 * 60 * 1000).toLong()

                Pair(
                    DateUtils.formatTime(today - threeMonthsInMs),
                    DateUtils.formatTime(today)
                )
            }
            CUSTOM_DATE -> {
                Pair("", "")
            }
        }
    }
}