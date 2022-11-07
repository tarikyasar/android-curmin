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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarikyasar.curmin.R
import com.tarikyasar.curmin.presentation.ui.theme.CurrencyTextColor
import com.tarikyasar.curmin.utils.CurrencyUtils
import com.tarikyasar.curmin.utils.insert

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
                            var valueText = it.text.filter { char ->
                                char.isDigit()
                            }

                            val value = when {
                                valueText.isEmpty() -> 0.0
                                else -> {
                                    valueText = valueText.insert(valueText.length - 2, '.')
                                    valueText.toDouble()
                                }
                            }

                            baseCurrencyText = TextFieldValue(
                                text = CurrencyUtils.formatCurrency(value),
                                selection = TextRange(CurrencyUtils.formatCurrency(value).length)
                            )
                            targetCurrencyText =
                                TextFieldValue(text = CurrencyUtils.formatCurrency(value * currencyRate))

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
                            .background(CurrencyTextColor)
                    )

                    Text(
                        text = baseCurrency,
                        color = CurrencyTextColor,
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
                            var valueText = it.text.filter { char ->
                                char.isDigit()
                            }

                            val value = when {
                                valueText.isEmpty() -> 0.0
                                else -> {
                                    valueText = valueText.insert(valueText.length - 2, '.')
                                    valueText.toDouble()
                                }
                            }

                            targetCurrencyText = TextFieldValue(
                                text = CurrencyUtils.formatCurrency(value),
                                selection = TextRange(CurrencyUtils.formatCurrency(value).length)
                            )
                            baseCurrencyText =
                                TextFieldValue(text = CurrencyUtils.formatCurrency(value / currencyRate))
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
                            .background(CurrencyTextColor)
                    )

                    Text(
                        text = targetCurrency,
                        color = CurrencyTextColor,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
        }
    }
}

