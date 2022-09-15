package com.tarikyasar.curmin.presentation.currency_symbol_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tarikyasar.curmin.presentation.composable.CurminDropdown

@Composable
fun CurrencySymbolListDropDown(
    viewModel: CurrencySymbolListDropDownViewModel = hiltViewModel(),
    onCurrencySelected: (String) -> Unit
) {
    val state = viewModel.state.value
    var expanded by remember { mutableStateOf(false) }
    var selectedCurrencyIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.height(100.dp)
    ) {
        CurminDropdown(
            expanded = expanded,
            onExpandedChangeRequest = {
                expanded = it
            },
            selectedItemText = if (state.currencySymbols.isEmpty()) "" else state.currencySymbols[0].code
        ) {
            state.currencySymbols.forEachIndexed { index, currency ->
                DropdownMenuItem(
                    onClick = {
                        selectedCurrencyIndex = index
                        expanded = false
                        onCurrencySelected(state.currencySymbols[selectedCurrencyIndex].code)
                    }
                ) {
                    Text(text = currency.code, color = MaterialTheme.colors.onSurface)
                }
            }
        }
    }

}