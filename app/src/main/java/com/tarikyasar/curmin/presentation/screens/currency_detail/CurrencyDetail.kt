package com.tarikyasar.curmin.presentation.screens.currency_detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.CurrencyConversionSection
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.CurrencyDetailTopBar
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.RefreshInformationSection
import com.tarikyasar.curmin.utils.DateUtils
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrencyDetail(
    onNavigateBack: () -> Unit,
    currency: CurrencyWatchlistItemData?,
    viewModel: CurrencyDetailViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            CurrencyDetailTopBar(
                baseCurrency = currency?.baseCurrencyCode,
                targetCurrency = currency?.targetCurrencyCode,
                onBackButtonClick = onNavigateBack
            )
        }
    ) {
        CurrencyDetailContent(
            baseCurrency = currency?.baseCurrencyCode,
            targetCurrency = currency?.targetCurrencyCode,
            date = currency?.date,
            rate = currency?.rate?.toString(),
            onDateSelect = { startDate, endDate ->
                viewModel.getLatestData(
                    startDate = startDate,
                    endDate = endDate,
                    baseCurrencyCode = currency?.baseCurrencyCode ?: "USD",
                    targetCurrencyCode = currency?.targetCurrencyCode ?: "TRY"
                )
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrencyDetailContent(
    baseCurrency: String?,
    targetCurrency: String?,
    date: String?,
    rate: String?,
    onDateSelect: (startDate: String, endDate: String) -> Unit
) {
    val context = LocalContext.current as AppCompatActivity
    val datePicker = MaterialDatePicker
        .Builder
        .dateRangePicker()
        .setCalendarConstraints(getDatePickerConstraints())
        .setTheme(com.tarikyasar.curmin.R.style.DatePickerDialogTheme)
        .build()

    var dateRangeStart by remember { mutableStateOf("") }
    var dateRangeEnd by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Divider()

        RefreshInformationSection(time = date ?: "")

        Divider()

        Button(onClick = {
            datePicker.addOnPositiveButtonClickListener {
                dateRangeStart = DateUtils.formatTime(it.first)
                dateRangeEnd = DateUtils.formatTime(it.second)
                onDateSelect(dateRangeStart, dateRangeEnd)
            }
            datePicker.show(context.supportFragmentManager, "")
        }) {
            Text("Open calendar")
        }

        if (dateRangeStart.isNotEmpty() && dateRangeEnd.isNotEmpty()) {
            Text(text = "Selected Date Range: $dateRangeStart - $dateRangeEnd")
        }

        CurrencyConversionSection(
            baseCurrency = baseCurrency ?: "",
            targetCurrency = targetCurrency ?: "",
            currencyRate = rate?.toDouble() ?: 0.0
        )
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