package com.tarikyasar.curmin.presentation.screens.currency_detail.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.tarikyasar.curmin.common.Constants
import com.tarikyasar.curmin.domain.SymbolListManager
import com.tarikyasar.curmin.presentation.ui.theme.CurrencyTextColor
import com.tarikyasar.curmin.utils.CurrencyUtils
import com.tarikyasar.curmin.utils.insert

@Composable
fun CurrencyConversionTextField(
    currencyName: String,
    currencyText: TextFieldValue,
    currencyRate: Double,
    onConversionCompleted: (TextFieldValue, TextFieldValue) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = currencyText,
            onValueChange = {
                if (it.text.length <= Constants.CURRENCY_CONVERSION_MAX_LENGTH) {
                    val (base, target) = updateCurrencyAmount(
                        amount = it,
                        currencyRate = currencyRate
                    )

                    onConversionCompleted(base, target)
                }
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
                    .background(CurrencyTextColor)
            )

            Text(
                text = getCurrencyName(currencyName, currencyText.text.length),
                color = CurrencyTextColor,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}

private fun getCurrencyName(
    currencyCode: String,
    inputLength: Int
): String {
    return if (inputLength >= Constants.CURRENCY_CONVERSION_LENGTH) {
        currencyCode
    } else {
        SymbolListManager.symbols.find { it.code == currencyCode }?.name ?: currencyCode
    }
}

private fun updateCurrencyAmount(
    amount: TextFieldValue,
    currencyRate: Double,
): Pair<TextFieldValue, TextFieldValue> {
    var valueText = amount.text.filter { char ->
        char.isDigit()
    }

    val value = when {
        valueText.isEmpty() -> 0.0
        else -> {
            valueText = valueText.insert(valueText.length - 2, '.')
            valueText.toDouble()
        }
    }

    val baseCurrencyText = TextFieldValue(
        text = CurrencyUtils.formatCurrency(value),
        selection = TextRange(CurrencyUtils.formatCurrency(value).length)
    )
    val targetCurrencyText =
        TextFieldValue(text = CurrencyUtils.formatCurrency(value * currencyRate))

    return Pair(baseCurrencyText, targetCurrencyText)
}
