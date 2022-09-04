package com.tarikyasar.curmin.presentation.currency_symbol_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CurrencySymbolList(
    viewModel: CurrencySymbolListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Symbols: ", fontSize = 24.sp, color = Color.White)

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.currencySymbols) { symbol ->
                Text(
                    text = "${symbol.code} : ${symbol.name}",
                    color = Color.White
                )
            }
        }
    }
}