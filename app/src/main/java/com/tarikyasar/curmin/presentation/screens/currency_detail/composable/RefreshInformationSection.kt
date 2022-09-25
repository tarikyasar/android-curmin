package com.tarikyasar.curmin.presentation.screens.currency_detail.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RefreshInformationSection(
    time: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Refreshed at: $time",
            color = MaterialTheme.colors.onBackground,
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}