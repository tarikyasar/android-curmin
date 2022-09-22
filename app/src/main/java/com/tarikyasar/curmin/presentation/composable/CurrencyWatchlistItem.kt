package com.tarikyasar.curmin.presentation.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarikyasar.curmin.R
import com.tarikyasar.curmin.presentation.ui.theme.*

@Composable
fun CurrencyWatchlistItem(
    base: String,
    target: String,
    value: Double,
    change: Double,
    date: String,
    onClick: () -> Unit
) {
    val rotationState by animateFloatAsState(targetValue = if (change < 0) 360f else 180f)

    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$base-$target",
                color = MaterialTheme.colors.onSurface
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = value.toString(),
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 16.sp
                )

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(if (change < 0) CurrencyDownColor else CurrencyUpColor),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_dropdown),
                        contentDescription = null,
                        tint = if (change < 0) CurrencyArrowDownColor else CurrencyArrowUpColor,
                        modifier = Modifier.rotate(rotationState)
                    )

                    Text(
                        text = change.toString(),
                        color = SurfaceColorDark,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

            Text(
                text = date,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}