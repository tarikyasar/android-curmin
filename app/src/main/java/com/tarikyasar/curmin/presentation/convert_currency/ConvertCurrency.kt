package com.tarikyasar.curmin.presentation.convert_currency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ConvertCurrency(
    convertCurrencyViewModel: ConvertCurrencyViewModel = hiltViewModel()
) {
    var state = convertCurrencyViewModel.state.value

    var fromCurrencySymbol by remember { mutableStateOf("") }
    var toCurrencySymbol by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CurminTextField(
            textFieldState = fromCurrencySymbol,
            onValueChange = { fromCurrencySymbol = it },
            hint = "From"
        )

        Spacer(Modifier.height(10.dp))

        CurminTextField(
            textFieldState = toCurrencySymbol,
            onValueChange = { toCurrencySymbol = it },
            hint = "To"
        )

        Spacer(Modifier.height(10.dp))

        CurminTextField(
            textFieldState = amount,
            onValueChange = { amount = it },
            hint = "Amount",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = {
                convertCurrencyViewModel.getSymbols(
                    fromCurrencySymbol = fromCurrencySymbol,
                    toCurrencySymbol = toCurrencySymbol,
                    amount = amount.toDouble()
                )
            },
            modifier = Modifier.background(MaterialTheme.colors.primary)
        ) {
            Text(text = "Convert")
        }

        Spacer(Modifier.height(10.dp))

        if (state.convertedCurrency != null) {
            Text(
                text = "Converted Amount: ${state.convertedCurrency?.amount} ${state.convertedCurrency?.fromCurrencyCode}",
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun CurminTextField(
    modifier: Modifier = Modifier,
    textFieldState: String,
    hint: String = "",
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = true,
    isEditable: Boolean = true,
    maxLength: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    onDone: (() -> Unit)? = null
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(Modifier.height(70.dp)) {
        OutlinedTextField(
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedLabelColor = MaterialTheme.colors.primary,
                backgroundColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            value = textFieldState,
            onValueChange = {
                if (isEditable && it.length <= maxLength) {
                    onValueChange(it)
                }
            },
            label = {
                Text(
                    hint,
                    color = if (isFocused) MaterialTheme.colors.primary else Color.White
                )
            },
            visualTransformation = visualTransformation,
            maxLines = maxLines,
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onDone?.invoke()
                },
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            modifier = modifier
                .fillMaxWidth()
                .onFocusEvent {
                    isFocused = it.isFocused
                }
        )
    }

}
