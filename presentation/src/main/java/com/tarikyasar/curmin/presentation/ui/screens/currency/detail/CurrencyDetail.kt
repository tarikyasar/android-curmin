package com.tarikyasar.curmin.presentation.ui.screens.currency.detail

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.presentation.composable.CurminErrorDialog
import com.tarikyasar.curmin.presentation.composable.CurminTopBar
import com.tarikyasar.curmin.presentation.ui.base.curminViewModel
import com.tarikyasar.curmin.presentation.ui.screens.currency.detail.CurrencyDetailContract.*
import com.tarikyasar.curmin.presentation.ui.screens.currency.detail.composable.RefreshInformationSection
import com.tarikyasar.curmin.presentation.ui.screens.currency.detail.composable.chart.LineChart
import com.tarikyasar.curmin.presentation.ui.screens.currency.detail.composable.conversion.CurrencyConversionSection
import com.tarikyasar.curmin.presentation.ui.screens.currency.detail.composable.date.CurrencyDateDropdown
import com.tarikyasar.curmin.utils.DateUtils
import com.tarikyasar.curmin.utils.DatesInMs

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrencyDetail(
    onNavigateBack: () -> Unit,
    currency: CurrencyWatchlistItemData?,
    viewModel: CurrencyDetailViewModel = curminViewModel()
) {
    val (uiState, onIntent, _) = viewModel

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                val today = System.currentTimeMillis()

                onIntent(
                    Intent.GetCurrencyTimeSeries(
                        startDate = DateUtils.formatTime(today.minus(DatesInMs.WEEK.value)),
                        endDate = DateUtils.formatTime(today),
                        baseCurrencyCode = currency?.baseCurrencyCode.orEmpty(),
                        targetCurrencyCode = currency?.targetCurrencyCode.orEmpty()
                    )
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
                            painter = painterResource(id = com.tarikyasar.curmin.presentation.R.drawable.ic_arrow_back),
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
        ) {
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.TopCenter
            ) {
                CurrencyDetailContent(
                    currency = currency,
                    uiState = uiState,
                    onIntent = onIntent
                )

                CurminErrorDialog(
                    showErrorDialog = uiState.error != null,
                    onDismissRequest = {
//                        viewModel.resetError()
                    },
                    onPositiveButtonClick = {
//                        viewModel.resetError()
                    },
                    errorMessage = ""
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrencyDetailContent(
    currency: CurrencyWatchlistItemData?,
    uiState: UiState,
    onIntent: (Intent) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(horizontal = 10.dp)
    ) {
        Divider()

        RefreshInformationSection(time = currency?.date.orEmpty())

        Divider()

        Spacer(Modifier.height(16.dp))

        CurrencyDateDropdown(
            onDateSelect = { startDate, endDate ->
                onIntent(
                    Intent.GetCurrencyTimeSeries(
                        startDate = startDate,
                        endDate = endDate,
                        baseCurrencyCode = currency?.baseCurrencyCode.orEmpty(),
                        targetCurrencyCode = currency?.targetCurrencyCode.orEmpty()
                    )
                )
            }
        )

        Spacer(Modifier.height(16.dp))

        uiState.currencyRates?.rates?.let { rates ->
            LineChart(
                data = rates,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        CurrencyConversionSection(
            baseCurrency = currency?.baseCurrencyCode.orEmpty(),
            targetCurrency = currency?.targetCurrencyCode.orEmpty(),
            currencyRate = currency?.rate ?: 0.0
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