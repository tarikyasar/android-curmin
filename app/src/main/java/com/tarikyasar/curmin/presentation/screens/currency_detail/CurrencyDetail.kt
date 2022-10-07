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
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.CurrencyConversionSection
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.CurrencyDetailTopBar
import com.tarikyasar.curmin.presentation.screens.currency_detail.composable.RefreshInformationSection
import com.tarikyasar.curmin.utils.DateUtils
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
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
        CurrencyDetailContent()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrencyDetailContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Divider()

        RefreshInformationSection(time = DateUtils.formatTime(LocalDateTime.now()))

        Divider()

        // TODO: Update these strings
        CurrencyConversionSection(
            baseCurrency = "United States Dollar",
            targetCurrency = "Turkish Lira",
            currencyRate = 18.42
        )
    }
}

