package com.tarikyasar.curmin.presentation.screens.currency_detail.composable.date

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.material.datepicker.MaterialDatePicker
import com.tarikyasar.curmin.common.DatesInMs
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

                "${DateUtils.formatTimeWithDay(today - DatesInMs.WEEK.value)} - ${DateUtils.formatTimeWithDay(today)}"
            }
            LAST_MONTH -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()

                "${DateUtils.formatTimeWithDay(today - DatesInMs.MONTH.value)} - ${DateUtils.formatTimeWithDay(today)}"
            }
            LAST_THREE_MONTHS -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()

                "${DateUtils.formatTimeWithDay(today - 3*DatesInMs.MONTH.value)} - ${DateUtils.formatTimeWithDay(today)}"
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

                Pair(DateUtils.formatTime(today - DatesInMs.WEEK.value), DateUtils.formatTime(today))
            }
            LAST_MONTH -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()

                Pair(DateUtils.formatTime(today - DatesInMs.MONTH.value), DateUtils.formatTime(today))
            }
            LAST_THREE_MONTHS -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()

                Pair(
                    DateUtils.formatTime(today - 3*DatesInMs.MONTH.value),
                    DateUtils.formatTime(today)
                )
            }
            CUSTOM_DATE -> {
                Pair("", "")
            }
        }
    }
}