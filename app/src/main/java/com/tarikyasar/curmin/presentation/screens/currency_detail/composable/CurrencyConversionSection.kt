package com.tarikyasar.curmin.presentation.screens.currency_detail.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CurrencyConversionSection(
    baseCurrency: String,
    targetCurrency: String,
    currencyRate: Double
) {
    var baseCurrencyText by remember { mutableStateOf("") }
    var targetCurrencyText by remember { mutableStateOf("") }

    Column {
        Text(
            text = "Currency Conversion",
            modifier = Modifier.align(Alignment.Start),
            fontSize = 24.sp
        )

        Spacer(Modifier.height(10.dp))

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = baseCurrencyText,
                    onValueChange = {
                        baseCurrencyText = it
                        targetCurrencyText = (baseCurrencyText.toDouble() * currencyRate).toString()
                    },
                    modifier = Modifier
                        .background(MaterialTheme.colors.surface)
                        .fillMaxWidth()
                )

                Row(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Divider(
                        modifier = Modifier
                            .height(40.dp)
                            .width(1.dp)
                            .background(MaterialTheme.colors.onSurface)
                    )

                    Text(
                        text = baseCurrency,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = targetCurrencyText,
                    onValueChange = {
//                        baseCurrencyText = it
//                        targetCurrencyText = (baseCurrencyText.toDouble() * currencyRate).toString()
                    },
                    modifier = Modifier
                        .background(MaterialTheme.colors.surface)
                        .fillMaxWidth()
                )

                Row(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Divider(
                        modifier = Modifier
                            .height(40.dp)
                            .width(1.dp)
                            .background(MaterialTheme.colors.onSurface)
                    )

                    Text(
                        text = targetCurrency,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
        }

    }
}