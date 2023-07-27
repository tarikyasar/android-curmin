package com.tarikyasar.curmin.presentation.ui.screens.currency.detail.composable.date

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.google.android.material.datepicker.MaterialDatePicker
import com.tarikyasar.curmin.presentation.R
import com.tarikyasar.curmin.utils.DateUtils
import com.tarikyasar.curmin.utils.DatesInMs

enum class DateSelection {
    LAST_WEEK,
    LAST_MONTH,
    LAST_THREE_MONTHS,
    LAST_SIX_MONTHS,
    LAST_YEAR;

    @Composable
    fun getDateSelectionName(): String {
        return when (this) {
            LAST_WEEK -> stringResource(id = R.string.last_week)
            LAST_MONTH -> stringResource(id = R.string.last_month)
            LAST_THREE_MONTHS -> stringResource(id = R.string.last_three_months)
            LAST_SIX_MONTHS -> stringResource(id = R.string.last_six_months)
            LAST_YEAR -> stringResource(id = R.string.last_year)
        }
    }

    fun getDateNames(): String {
        return when (this) {
            LAST_WEEK -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()

                "${DateUtils.formatTimeWithDay(today - DatesInMs.WEEK.value)} - ${
                    DateUtils.formatTimeWithDay(
                        today
                    )
                }"
            }
            LAST_MONTH -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()

                "${DateUtils.formatTimeWithDay(today - DatesInMs.MONTH.value)} - ${
                    DateUtils.formatTimeWithDay(
                        today
                    )
                }"
            }
            LAST_THREE_MONTHS -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()

                "${DateUtils.formatTimeWithDay(today - 3 * DatesInMs.MONTH.value)} - ${
                    DateUtils.formatTimeWithDay(
                        today
                    )
                }"
            }
            LAST_SIX_MONTHS -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()

                "${DateUtils.formatTimeWithDay(today - 6 * DatesInMs.MONTH.value)} - ${
                    DateUtils.formatTimeWithDay(
                        today
                    )
                }"
            }
            LAST_YEAR -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()

                "${DateUtils.formatTimeWithDay(today - DatesInMs.YEAR.value)} - ${
                    DateUtils.formatTimeWithDay(
                        today
                    )
                }"
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateRanges(): Pair<String, String> {
        return when (this) {
            LAST_WEEK -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()

                Pair(
                    DateUtils.formatTime(today - DatesInMs.WEEK.value),
                    DateUtils.formatTime(today)
                )
            }
            LAST_MONTH -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()

                Pair(
                    DateUtils.formatTime(today - DatesInMs.MONTH.value),
                    DateUtils.formatTime(today)
                )
            }
            LAST_THREE_MONTHS -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()

                Pair(
                    DateUtils.formatTime(today - 3 * DatesInMs.MONTH.value),
                    DateUtils.formatTime(today)
                )
            }
            LAST_SIX_MONTHS -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()

                Pair(
                    DateUtils.formatTime(today - 6 * DatesInMs.MONTH.value),
                    DateUtils.formatTime(today)
                )
            }
            LAST_YEAR -> {
                val today = MaterialDatePicker.todayInUtcMilliseconds()

                Pair(
                    DateUtils.formatTime(today - DatesInMs.YEAR.value),
                    DateUtils.formatTime(today)
                )
            }
        }
    }
}