package com.tarikyasar.curmin.presentation.screens.currency_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CurrencyDetail(
    onNavigate: () -> Unit,
    baseCurrency: String? = "",
    targetCurrency: String? = ""
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        Text(text = "$baseCurrency-$targetCurrency")

        Spacer(Modifier.height(10.dp))

        Text("Go Back", modifier = Modifier.clickable {
            onNavigate()
        })
    }
}