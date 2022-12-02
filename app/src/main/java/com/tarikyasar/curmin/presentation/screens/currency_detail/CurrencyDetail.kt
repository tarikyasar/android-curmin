package com.tarikyasar.curmin.presentation.screens.currency_detail

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.himanshoe.charty.line.model.LineData
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.CurrencyDetailTopBar
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.RefreshInformationSection
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.chart.CurrencyRateChart
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.conversion.CurrencyConversionSection
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.date.CurrencyDateDropdown

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrencyDetail(
    onNavigateBack: () -> Unit,
    currency: CurrencyWatchlistItemData?,
    viewModel: CurrencyDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Box {
        Scaffold(
            topBar = {
                CurrencyDetailTopBar(
                    baseCurrency = currency?.baseCurrencyCode,
                    targetCurrency = currency?.targetCurrencyCode,
                    onBackButtonClick = onNavigateBack
                )
            },
            modifier = Modifier.blur(if (state.isLoading) 50.dp else 0.dp),
        ) {
            CurrencyDetailContent(
                baseCurrency = currency?.baseCurrencyCode,
                targetCurrency = currency?.targetCurrencyCode,
                date = currency?.date,
                rate = currency?.rate?.toString(),
                rates = state.currencyRates,
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

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrencyDetailContent(
    baseCurrency: String?,
    targetCurrency: String?,
    date: String?,
    rate: String?,
    rates: List<LineData>,
    onDateSelect: (startDate: String, endDate: String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Divider()

        RefreshInformationSection(time = date ?: "")

        Divider()

        Spacer(Modifier.height(16.dp))

        CurrencyDateDropdown(
            onDateSelect = { startDate, endDate ->
                onDateSelect(startDate, endDate)
            }
        )

        Spacer(Modifier.height(16.dp))

        CurrencyRateChart(rates)

        CurrencyConversionSection(
            baseCurrency = baseCurrency ?: "",
            targetCurrency = targetCurrency ?: "",
            currencyRate = rate?.toDouble() ?: 0.0
        )
    }
}