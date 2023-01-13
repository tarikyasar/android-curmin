package com.tarikyasar.curmin.presentation.screens.currency_detail

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.tarikyasar.curmin.common.DatesInMs
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.presentation.composable.CurminErrorDialog
import com.tarikyasar.curmin.presentation.composable.CurminTopBar
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.RefreshInformationSection
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.chart.LineChart
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.conversion.CurrencyConversionSection
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.date.CurrencyDateDropdown
import com.tarikyasar.curmin.utils.DateUtils

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrencyDetail(
    onNavigateBack: () -> Unit,
    currency: CurrencyWatchlistItemData?,
    viewModel: CurrencyDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                val today = System.currentTimeMillis()

                viewModel.getCurrencyTimeSeries(
                    startDate = DateUtils.formatTime(today.minus(DatesInMs.WEEK.value)),
                    endDate = DateUtils.formatTime(today),
                    baseCurrencyCode = currency?.baseCurrencyCode.orEmpty(),
                    targetCurrencyCode = currency?.targetCurrencyCode.orEmpty()
                )
            }
            else -> Unit
        }
    }

    Box {
        Scaffold(
            topBar = {
                CurminTopBar(
                    leadingButton = {
                        Icon(
                            painter = painterResource(id = com.tarikyasar.curmin.R.drawable.ic_arrow_back),
                            contentDescription = null,
                            tint = MaterialTheme.colors.onBackground,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .clickable {
                                    onNavigateBack()
                                }
                        )
                    },
                    title = "${currency?.baseCurrencyCode} - ${currency?.targetCurrencyCode}",
                    trailingButton = {
                        Box(modifier = Modifier.size(32.dp))
                    }
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .blur(if (state.isLoading) 50.dp else 0.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.TopCenter
            ) {
                CurrencyDetailContent(
                    baseCurrency = currency?.baseCurrencyCode,
                    targetCurrency = currency?.targetCurrencyCode,
                    date = currency?.date,
                    rate = currency?.rate?.toString(),
                    rates = state.currencyRates.orEmpty(),
                    onDateSelect = { startDate, endDate ->
                        viewModel.getCurrencyTimeSeries(
                            startDate = startDate,
                            endDate = endDate,
                            baseCurrencyCode = currency?.baseCurrencyCode ?: "USD",
                            targetCurrencyCode = currency?.targetCurrencyCode ?: "TRY"
                        )
                    }
                )

                CurminErrorDialog(
                    showErrorDialog = state.error != null,
                    onDismissRequest = {
                        viewModel.resetError()
                    },
                    onPositiveButtonClick = {
                        viewModel.resetError()
                    },
                    errorMessage = state.error?.getErrorMessage() ?: ""
                )
            }
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
    rates: List<Pair<Int, Double>>,
    onDateSelect: (startDate: String, endDate: String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(horizontal = 10.dp)
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

        if (rates.isNotEmpty()) {
            LineChart(
                data = rates,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        CurrencyConversionSection(
            baseCurrency = baseCurrency ?: "",
            targetCurrency = targetCurrency ?: "",
            currencyRate = rate?.toDouble() ?: 0.0
        )
    }
}

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}