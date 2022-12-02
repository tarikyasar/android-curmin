package com.tarikyasar.curmin.presentation.screens.currency_detail.composable.date

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.tarikyasar.curmin.presentation.composable.CurminDateDropdown
import com.tarikyasar.curmin.utils.DateUtils
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrencyDateDropdown(
    onDateSelect: (startDate: String, endDate: String) -> Unit
) {
    val context = LocalContext.current as AppCompatActivity
    val datePicker = MaterialDatePicker
        .Builder
        .dateRangePicker()
        .setCalendarConstraints(getDatePickerConstraints())
        .setTheme(com.tarikyasar.curmin.R.style.DatePickerDialogTheme)
        .build()

    var dateSelectionDropdownExpanded by remember { mutableStateOf(false) }
    var extraText by remember { mutableStateOf("") }
    var dateState by remember { mutableStateOf(DateSelection.LAST_WEEK) }

    CurminDateDropdown(
        expanded = dateSelectionDropdownExpanded,
        onExpandedChangeRequest = { dateSelectionDropdownExpanded = it },
        selectedItemText = dateState.getDateSelectionName(),
        extraText = extraText
    ) {
        DateSelection.values().forEach { date ->
            DropdownMenuItem(
                onClick = {
                    dateState = date
                    dateSelectionDropdownExpanded = false

                    if (dateState == DateSelection.CUSTOM_DATE) {
                        datePicker.addOnPositiveButtonClickListener {
                            extraText =
                                "${DateUtils.formatTimeWithDay(it.first)} - ${DateUtils.formatTimeWithDay(it.second)}"

                            onDateSelect(
                                DateUtils.formatTime(it.first),
                                DateUtils.formatTime(it.second)
                            )
                        }

                        datePicker.show(context.supportFragmentManager, "")
                    } else {
                        extraText = dateState.getDateNames()

                        onDateSelect(
                            dateState.getDateRanges().first,
                            dateState.getDateRanges().second
                        )
                    }
                }
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = date.getDateSelectionName(),
                        fontSize = 20.sp
                    )

                    Text(
                        text = date.getDateNames(),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

private fun getDatePickerConstraints(): CalendarConstraints {
    val today = MaterialDatePicker.todayInUtcMilliseconds()
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    calendar.timeInMillis = today
    calendar[Calendar.YEAR] = SimpleDateFormat("yyyy").format(today).toInt() - 1
    val startDate = calendar.timeInMillis

    return CalendarConstraints.Builder()
        .setStart(startDate)
        .setEnd(today)
        .build()
}