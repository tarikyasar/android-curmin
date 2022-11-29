package com.tarikyasar.curmin.presentation.screens.currency_detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.CurrencyConversionSection
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.CurrencyDetailTopBar
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.RefreshInformationSection

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrencyDetail(
    onNavigateBack: () -> Unit,
    currency: CurrencyWatchlistItemData?,
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
            rate = currency?.rate?.toString()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrencyDetailContent(
    baseCurrency: String?,
    targetCurrency: String?,
    date: String?,
    rate: String?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Divider()

        RefreshInformationSection(time = date ?: "")

        Divider()

        CurrencyConversionSection(
            baseCurrency = baseCurrency ?: "",
            targetCurrency = targetCurrency ?: "",
            currencyRate = rate?.toDouble() ?: 0.0
        )
    }
}

