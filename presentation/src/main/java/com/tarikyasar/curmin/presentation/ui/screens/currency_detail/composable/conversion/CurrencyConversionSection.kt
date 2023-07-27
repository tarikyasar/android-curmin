package com.tarikyasar.curmin.presentation.ui.screens.currency_detail.composable.conversion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarikyasar.curmin.utils.CurrencyUtils

@Composable
fun CurrencyConversionSection(
    baseCurrency: String,
    targetCurrency: String,
    currencyRate: Double
) {
    var baseCurrencyText by remember {
        mutableStateOf(
            TextFieldValue(
                CurrencyUtils.formatCurrency(
                    0.0
                )
            )
        )
    }
    var targetCurrencyText by remember {
        mutableStateOf(
            TextFieldValue(
                CurrencyUtils.formatCurrency(
                    0.0
                )
            )
        )
    }

    Column {
        Text(
            text = stringResource(id = R.string.currency_conversion),
            modifier = Modifier.align(Alignment.Start),
            fontSize = 24.sp
        )

        Spacer(Modifier.height(10.dp))

        Column {
            CurrencyConversionTextField(
                currencyName = baseCurrency,
                currencyText = baseCurrencyText,
                currencyRate = currencyRate,
                onConversionCompleted = { base, target ->
                    baseCurrencyText = base
                    targetCurrencyText = target
                }
            )

            Spacer(Modifier.height(10.dp))

            CurrencyConversionTextField(
                currencyName = targetCurrency,
                currencyText = targetCurrencyText,
                currencyRate = 1 / currencyRate,
                onConversionCompleted = { base, target ->
                    targetCurrencyText = base
                    baseCurrencyText = target
                }
            )
        }
    }
}

