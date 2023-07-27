package com.tarikyasar.curmin.presentation.ui.screens.currency.detail.composable.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarikyasar.curmin.presentation.R

@Composable
fun CurrencyRateChart(
    rates: List<Pair<Int, Double>>? = emptyList()
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(200.dp)
    ) {
        if (rates.isNullOrEmpty().not()) {
            LineChart(
                rates!!
            )
        } else {
            EmptyChart()
        }
    }
}

@Composable
fun EmptyChart() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_empty),
            contentDescription = null,
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier
                .size(48.dp)
        )

        Text(
            text = stringResource(id = R.string.no_chart_data),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier.padding(10.dp)
        )
    }
}