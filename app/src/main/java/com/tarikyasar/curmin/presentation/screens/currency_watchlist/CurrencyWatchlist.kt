package com.tarikyasar.curmin.presentation.screens.currency_watchlist

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.tarikyasar.curmin.presentation.composable.CurminWarningDialog
import com.tarikyasar.curmin.presentation.composable.currency_watchlist_item.CurrencyWatchlistItem
import com.tarikyasar.curmin.presentation.ui.theme.SwipeDeleteButtonBackgroundColor
import com.tarikyasar.curmin.presentation.ui.theme.SwipeDeleteButtonLabelColor
import kotlin.math.roundToInt
import kotlin.random.Random

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CurrencyWatchlist(
    viewModel: CurrencyWatchlistViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    var changeValue1 by remember { mutableStateOf(Random.nextDouble(-0.25, 0.25)) }
    var showDeleteWatchlistItemDialog by remember { mutableStateOf(false) }
    var listItemUidToDelete by remember { mutableStateOf(0) }
    val swipeRefreshState by remember { mutableStateOf(SwipeRefreshState(false)) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Box {
            Column(
                verticalArrangement = if (state.currencies.isNotEmpty()) Arrangement.Top else Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {

                if (state.currencies.isNotEmpty()) {
                    SwipeRefresh(
                        state = swipeRefreshState,
                        onRefresh = {
                            viewModel.getCurrencies()
                            changeValue1 = Random.nextDouble(-0.5, 0.5)
                        }
                    ) {
                        LazyColumn(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxHeight()
                        ) {
                            items(state.currencies) { currency ->
                                val dismissState = rememberDismissState(
                                    confirmStateChange = {
                                        if (it == DismissValue.DismissedToStart) {
                                            listItemUidToDelete = currency.uid
                                            showDeleteWatchlistItemDialog = true
                                            false
                                        } else {
                                            true
                                        }
                                    }
                                )

                                SwipeToDismiss(
                                    state = dismissState,
                                    background = {
                                        val color = when (dismissState.dismissDirection) {
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
                                            target = currency.baseCurrencyCode ?: "",
                                            value = ((currency.rate
                                                ?: 0.0) * 100.0).roundToInt() / 100.0,
                                            change = (changeValue1 * 100.0).roundToInt() / 100.0,
                                            date = "14.09.2022"
                                        )
                                    },
                                    directions = setOf(DismissDirection.EndToStart)
                                )

                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                } else if (state.isLoading.not()) {
                    Text(
                        text = "There is no currency on the list. You can add them with the button down below.",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            if (state.isLoading || swipeRefreshState.isRefreshing) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            Button(
                onClick = {
                    viewModel.insertCurrency(
                        com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItem(
                            uid = 0,
                            baseCurrencyCode = "USD",
                            targetCurrencyCode = "TRY",
                            rate = 10.0,
                            previousChangeRate = 0.04,
                            currentChangeRate = 0.2,
                            date = "17.09.2022"
                        )
                    )
                },
                shape = CircleShape,
                colors = ButtonDefaults
                    .buttonColors(backgroundColor = MaterialTheme.colors.primary),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                )
            }

            DeleteWatchlistItemDialog(
                showDeleteWatchlistItemDialog = showDeleteWatchlistItemDialog,
                onDismissRequest = {
                    showDeleteWatchlistItemDialog = false
                },
                onPositiveButtonClick = {
                    viewModel.deleteCurrency(listItemUidToDelete)
                },
                onNegativeButtonClick = { showDeleteWatchlistItemDialog = false },
                baseCurrency = "XXX",
                targetCurrency = "TRY"
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