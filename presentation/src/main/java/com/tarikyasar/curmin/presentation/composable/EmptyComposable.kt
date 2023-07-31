package com.tarikyasar.curmin.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyComposable(
    text: String,
    iconPainter: Painter,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Icon(
            painter = iconPainter,
            contentDescription = null,
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier
                .size(70.dp)
        )

        Text(
            text = text,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier.padding(10.dp)
        )

        Text(
            text = buttonText,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 20.sp,
            modifier = Modifier
                .clickable { onButtonClick() }
                .background(MaterialTheme.colors.primary, RoundedCornerShape(8.dp))
                .padding(horizontal = 32.dp, vertical = 8.dp)
        )
    }
}