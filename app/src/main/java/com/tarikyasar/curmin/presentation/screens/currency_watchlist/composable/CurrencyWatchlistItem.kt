package com.tarikyasar.curmin.presentation.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarikyasar.curmin.R
import com.tarikyasar.curmin.presentation.ui.theme.*
import com.tarikyasar.curmin.utils.formatDate
import kotlin.math.roundToInt

@Composable
fun SwipeableCurrencyWatchlistItem(
    base: String,
    target: String,
    value: Double,
    change: Double,
    date: String,
    onItemClick: () -> Unit,
    backgroundColor: Color,
    icon: Painter,
    onSwipeAction: () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    var isSwipeLocked by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(backgroundColor)
                .clickable { onSwipeAction() }
        ) {
            Icon(
                painter = icon,
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(46.dp)
                    .padding(end = 12.dp),
                tint = CurrencyArrowDownColor
            )

            CurrencyWatchlistItem(
                base = base,
                target = target,
                value = value,
                change = change,
                date = date,
                onClick = {
                    if (isSwipeLocked) {
                        offsetX = 0f
                        isSwipeLocked = false
                    } else {
                        onItemClick()
                    }
                },
                modifier = Modifier
                    .offset {
                        IntOffset(offsetX.roundToInt(), 0)
                    }
                    .draggable(
                        orientation = Orientation.Horizontal,
                        state = rememberDraggableState { delta ->
                            handleDragAmount(
                                isSwipeLocked,
                                offsetX,
                                delta
                            ) { location ->
                                offsetX = location
                            }
                        },
                        onDragStopped = {
                            if (offsetX <= -175f) {
                                offsetX = -175f
                                isSwipeLocked = true
                            } else {
                                offsetX = 0f
                                isSwipeLocked = false
                            }
                        }
                    ),
            )
        }
    }
}

private fun handleDragAmount(
    isSwipeLocked: Boolean,
    offsetX: Float,
    delta: Float,
    onChange: (result: Float) -> Unit
) {
    if (isSwipeLocked && delta >= 0) {
        onChange(offsetX + delta)

        if (offsetX >= 0) {
            onChange(0f)
        }
    } else if (delta <= 0) {
        onChange(offsetX + delta)

        if (offsetX <= -200f) {
            onChange(-200f)
        }
    }
}

@Composable
fun CurrencyWatchlistItem(
    base: String,
    target: String,
    value: Double,
    change: Double,
    date: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rotationState by animateFloatAsState(targetValue = if (change < 0) 360f else 180f)

    Surface(
        modifier = modifier
            .height(70.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
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

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = date.formatDate().first,
                    color = MaterialTheme.colors.onSurface
                )

                Text(
                    text = date.formatDate().second,
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 12.sp
                )
            }

        }
    }
}