package com.tarikyasar.curmin.presentation.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tarikyasar.curmin.R

@Composable
fun CurminDropdown(
    expanded: Boolean,
    onExpandedChangeRequest: (Boolean) -> Unit,
    selectedItemText: String,
    content: @Composable ColumnScope.() -> Unit
) {
    val rotationState by animateFloatAsState(targetValue = if (expanded) 180f else 360f)
    val alpha = animateFloatAsState(
        targetValue = if (expanded) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300
        )
    )
    val rotateX = animateFloatAsState(
        targetValue = if (expanded) 0f else -90f,
        animationSpec = tween(
            durationMillis = 300
        )
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = { onExpandedChangeRequest(true) })
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(8.dp)
        ) {
            Text(
                selectedItemText,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(Modifier.width(8.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_dropdown),
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.rotate(rotationState)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChangeRequest(false) },
            modifier = Modifier
                .graphicsLayer {
                    transformOrigin = TransformOrigin(2f, 0f)
                    rotationX = rotateX.value
                }
                .alpha(alpha.value)
        ) {
            content()
        }
    }
}