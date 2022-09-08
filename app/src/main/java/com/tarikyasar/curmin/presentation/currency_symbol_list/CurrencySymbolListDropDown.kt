package com.tarikyasar.curmin.presentation.currency_symbol_list

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tarikyasar.curmin.R

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
        CurrencyDropDown(
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

@Composable
fun CurrencyDropDown(
    expanded: Boolean,
    onExpandedChangeRequest: (Boolean) -> Unit,
    selectedItemText: String,
    content: @Composable ColumnScope.() -> Unit
) {
    val rotationState by animateFloatAsState(targetValue = if (expanded) 360f else 180f)
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
            .clickable(onClick = { onExpandedChangeRequest(true) })
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(20)
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
                .height(250.dp)
                .width(80.dp)
        ) {
            content()
        }
    }
}