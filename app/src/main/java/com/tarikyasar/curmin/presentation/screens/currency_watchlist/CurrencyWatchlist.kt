package com.tarikyasar.curmin.presentation.screens.currency_watchlist

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tarikyasar.curmin.R
import com.tarikyasar.curmin.domain.model.Currency
import com.tarikyasar.curmin.presentation.composable.CurminWarningDialog
import com.tarikyasar.curmin.presentation.composable.currency_watchlist_item.CurrencyWatchlistItem
import com.tarikyasar.curmin.presentation.screens.settings_dialog.SettingsDialog
import com.tarikyasar.curmin.presentation.ui.theme.SwipeDeleteButtonBackgroundColor
import com.tarikyasar.curmin.presentation.ui.theme.SwipeDeleteButtonLabelColor
import kotlin.math.roundToInt
import kotlin.random.Random

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CurrencyWatchlist() {
    var changeValue1 by remember { mutableStateOf(Random.nextDouble(-0.25, 0.25)) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var showDeleteWatchlistItemDialog by remember { mutableStateOf(false) }
    var list = mutableStateListOf(
        Currency("USD", Random.nextDouble(5.0, 20.0)),
        Currency("TRY", Random.nextDouble(5.0, 20.0)),
        Currency("EUR", Random.nextDouble(5.0, 20.0)),
        Currency("AZN", Random.nextDouble(5.0, 20.0)),
        Currency("AUD", Random.nextDouble(5.0, 20.0)),
        Currency("USD", Random.nextDouble(5.0, 20.0)),
        Currency("TRY", Random.nextDouble(5.0, 20.0)),
        Currency("EUR", Random.nextDouble(5.0, 20.0)),
        Currency("AZN", Random.nextDouble(5.0, 20.0)),
        Currency("AUD", Random.nextDouble(5.0, 20.0)),
    )
    var listItemToDelete: Currency? = null

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Box {
            Column {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = null,
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                showSettingsDialog = true
                            }
                    )
                }

                LazyColumn(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)
                ) {
                    itemsIndexed(
                        list,
                        key = { _, currencyItem -> currencyItem.hashCode() }
                    ) { _, currency ->
                        var state = rememberDismissState(
                            confirmStateChange = {
                                if (it == DismissValue.DismissedToStart) {
                                    showDeleteWatchlistItemDialog = true
                                    listItemToDelete = currency
                                    false
                                } else {
                                    true
                                }
                            }
                        )

                        SwipeToDismiss(
                            state = state,
                            background = {
                                val color = when (state.dismissDirection) {
                                    DismissDirection.EndToStart -> SwipeDeleteButtonBackgroundColor
                                    else -> Color.Transparent
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color, RoundedCornerShape(10.dp))
                                        .padding(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = SwipeDeleteButtonLabelColor,
                                        modifier = Modifier.align(Alignment.CenterEnd)
                                    )
                                }
                            },
                            dismissContent = {
                                CurrencyWatchlistItem(
                                    base = "XXX",
                                    target = currency.currencyCode,
                                    value = (currency.currencyRate * 100.0).roundToInt() / 100.0,
                                    change = (changeValue1 * 100.0).roundToInt() / 100.0,
                                    date = "14.09.2022"
                                )
                            },
                            directions = setOf(DismissDirection.EndToStart)
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                Button(
                    onClick = {
                        changeValue1 = Random.nextDouble(-0.25, 0.25)
                    },
                    content = {
                        Text("Randomize")
                    }
                )
            }

            SettingsDialog(
                openSettingsDialog = showSettingsDialog,
                onDismissRequest = { showSettingsDialog = false }
            )

            DeleteWatchlistItemDialog(
                showDeleteWatchlistItemDialog = showDeleteWatchlistItemDialog,
                onDismissRequest = {
                    showDeleteWatchlistItemDialog = false
                },
                onPositiveButtonClick = { list.remove(listItemToDelete) },
                onNegativeButtonClick = { showDeleteWatchlistItemDialog = false },
                baseCurrency = "XXX",
                targetCurrency = listItemToDelete?.currencyCode ?: ""
            )
        }
    }
}

@Composable
fun DeleteWatchlistItemDialog(
    showDeleteWatchlistItemDialog: Boolean,
    onDismissRequest: () -> Unit,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit,
    baseCurrency: String,
    targetCurrency: String,
) {
    CurminWarningDialog(
        showWarningDialog = showDeleteWatchlistItemDialog,
        onDismissRequest = onDismissRequest,
        onPositiveButtonClick = onPositiveButtonClick,
        onNegativeButtonClick = onNegativeButtonClick,
        warningMessage = "Watchlist item containing $baseCurrency-$targetCurrency currencies will be deleted.\nAre you sure?"
    )
}