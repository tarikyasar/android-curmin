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
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarikyasar.curmin.R
import com.tarikyasar.curmin.utils.CurrencyUtils

@Composable
fun CurrencyConversionSection(
    baseCurrency: String,
    targetCurrency: String,
    currencyRate: Double
) {
    var baseCurrencyText by remember { mutableStateOf(CurrencyUtils.formatCurrency(0.0)) }
    var targetCurrencyText by remember { mutableStateOf(CurrencyUtils.formatCurrency(0.0)) }
    var isBaseEditing by remember { mutableStateOf(true) }

    Column {
        Text(
            text = stringResource(id = R.string.currency_conversion),
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
                        if (isBaseEditing) {
                            baseCurrencyText = it
                            targetCurrencyText =
                                CurrencyUtils.formatCurrency(if (baseCurrencyText.isEmpty()) 0.0 else baseCurrencyText.toDouble() * currencyRate)
                        }
                    },
                    modifier = Modifier
                        .background(MaterialTheme.colors.surface)
                        .fillMaxWidth()
                        .onFocusEvent {
                            if (it.isFocused) {
                                isBaseEditing = true
                            }
                        }
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
                        if (!isBaseEditing) {
                            targetCurrencyText = it
                            baseCurrencyText =
                                CurrencyUtils.formatCurrency(if (targetCurrencyText.isEmpty()) 0.0 else targetCurrencyText.toDouble() / currencyRate)
                        }
                    },
                    modifier = Modifier
                        .background(MaterialTheme.colors.surface)
                        .fillMaxWidth()
                        .onFocusEvent {
                            if (it.isFocused) {
                                isBaseEditing = false
                            }
                        }
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

