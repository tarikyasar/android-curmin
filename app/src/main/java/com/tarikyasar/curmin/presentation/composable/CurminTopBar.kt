package com.tarikyasar.curmin.presentation.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CurminTopBar(
    leadingButton: @Composable () -> Unit = { Box(modifier = Modifier.size(32.dp)) },
    title: String = stringResource(id = com.tarikyasar.curmin.R.string.app_name),
    trailingButton: @Composable () -> Unit = { Box(modifier = Modifier.size(32.dp)) },
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        leadingButton()

        Text(
            text = title,
            fontSize = 24.sp,
            color = MaterialTheme.colors.onBackground
        )

        trailingButton()
    }
}