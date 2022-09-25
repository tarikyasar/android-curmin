package com.tarikyasar.curmin.presentation.screens.currency_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.CurrencyConversionSection
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.CurrencyDetailTopBar
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.RefreshInformationSection

@Composable
fun CurrencyDetail(
    onNavigateBack: () -> Unit,
    baseCurrency: String? = "",
    targetCurrency: String? = ""
) {
    Scaffold(
        topBar = {
            CurrencyDetailTopBar(
                baseCurrency = baseCurrency,
                targetCurrency = targetCurrency,
                onBackButtonClick = onNavigateBack
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Divider()

            RefreshInformationSection(time = "14:04:45 25.09.2022")

            Divider()

            CurrencyConversionSection(
                baseCurrency = "United States Dollar",
                targetCurrency = "Turkish Lira",
                currencyRate = 18.42
            )
        }
    }
}

