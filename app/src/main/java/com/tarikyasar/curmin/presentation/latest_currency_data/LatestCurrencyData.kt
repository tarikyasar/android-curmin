package com.tarikyasar.curmin.presentation.latest_currency_data

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
fun LatestCurrencyData(
    viewModel: LatestCurrencyDataViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            state.latestData?.let {
                items(it.currencies) { currency ->
                    Text(
                        text = "${currency.currencyCode} : ${currency.currencyRate}",
                        fontSize = 24.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}