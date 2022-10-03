package com.tarikyasar.curmin.presentation.screens.currency_watchlist.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarikyasar.curmin.R

@Composable
fun CurrencyWatchlistTopBar(
    onAddButtonClick: () -> Unit,
    onSettingsButtonClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    onAddButtonClick()
                }
                .size(34.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = MaterialTheme.colors.background,
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(
                        MaterialTheme.colors.onBackground,
                        CircleShape
                    )
                    .size(32.dp)
            )
        }

        Text(
            text = "Curmin",
            color = MaterialTheme.colors.onBackground,
            fontSize = 24.sp
        )

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    onSettingsButtonClick()
                }
                .size(34.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(
                        MaterialTheme.colors.background,
                        CircleShape
                    )
                    .size(32.dp)
            )
        }
    }
}