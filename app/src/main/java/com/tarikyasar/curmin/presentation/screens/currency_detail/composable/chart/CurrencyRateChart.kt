package com.tarikyasar.curmin.presentation.screens.currency_detail.composable.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.line.LineChart
import com.himanshoe.charty.line.config.LineConfig
import com.himanshoe.charty.line.model.LineData
import com.tarikyasar.curmin.R
import com.tarikyasar.curmin.presentation.ui.theme.CurrencyTextColor

@Composable
fun CurrencyRateChart(
    rates: List<LineData>
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
        if (rates.isNotEmpty()) {
            RatesLineChart(rates = rates)
        } else {
            EmptyChart()
        }
    }
}

@Composable
fun RatesLineChart(
    rates: List<LineData>
) {
    LineChart(
        lineData = rates,
        color = MaterialTheme.colors.primary,
        axisConfig = AxisConfig(
            showAxis = true,
            isAxisDashed = true,
            showUnitLabels = true,
            showXLabels = true,
            xAxisColor = CurrencyTextColor,
            yAxisColor = Color.Red,
            textColor = MaterialTheme.colors.onSurface
        ),
        lineConfig = LineConfig(
            hasDotMarker = false
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(vertical = 24.dp, horizontal = 16.dp)
    )
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