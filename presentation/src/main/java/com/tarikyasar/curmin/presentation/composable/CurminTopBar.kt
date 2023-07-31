package com.tarikyasar.curmin.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarikyasar.curmin.presentation.R

@Composable
fun CurminTopBar(
    leadingButton: @Composable () -> Unit = { Box(modifier = Modifier.size(32.dp)) },
    title: String = stringResource(id = R.string.app_name),
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