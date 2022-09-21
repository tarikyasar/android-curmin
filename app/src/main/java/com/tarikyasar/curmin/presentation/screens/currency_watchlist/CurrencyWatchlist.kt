package com.tarikyasar.curmin.presentation.screens.currency_watchlist

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.tarikyasar.curmin.R
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.domain.model.Symbol
import com.tarikyasar.curmin.presentation.composable.CurminErrorDialog
import com.tarikyasar.curmin.presentation.composable.CurrencyWatchlistItem
import com.tarikyasar.curmin.presentation.screens.currency_watchlist.composable.AddToWatchlistDialog
import com.tarikyasar.curmin.presentation.screens.currency_watchlist.composable.DeleteWatchlistItemDialog
import com.tarikyasar.curmin.presentation.screens.settings_dialog.SettingsDialog
import com.tarikyasar.curmin.presentation.ui.theme.SwipeDeleteButtonBackgroundColor
import com.tarikyasar.curmin.presentation.ui.theme.SwipeDeleteButtonLabelColor
import java.util.*
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
    var showAddToWatchlistDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(state.error.isEmpty().not()) }
    val swipeRefreshState by remember { mutableStateOf(SwipeRefreshState(false)) }
    var deleteItem by remember {
        mutableStateOf(
            CurrencyWatchlistItemData(
                "",
                null,
                null,
                null,
                null,
                null,
                null
            )
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                showAddToWatchlistDialog = true
                            }
                            .size(34.dp)
                    ) {
                        if (showAddToWatchlistDialog) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .clip(CircleShape)
                                    .size(34.dp)
                                    .background(MaterialTheme.colors.primary)
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.Add,
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

                    Text(
                        text = "Curmin",
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 24.sp
                    )

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                showSettingsDialog = true
                            }
                            .size(34.dp)
                    ) {
                        if (showSettingsDialog) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .clip(CircleShape)
                                    .size(34.dp)
                                    .background(MaterialTheme.colors.primary)
                            )
                        }

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
                                key(currency.uid) {
                                    val dismissState = rememberDismissState(
                                        confirmStateChange = {
                                            if (it == DismissValue.DismissedToStart) {
                                                deleteItem = currency
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
                                                base = currency.baseCurrencyCode ?: "",
                                                target = currency.targetCurrencyCode ?: "",
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
                    }
                } else if (state.isLoading.not()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_empty),
                            contentDescription = null,
                            tint = MaterialTheme.colors.secondary,
                            modifier = Modifier
                                .size(70.dp)
                        )

                        Text(
                            text = "There is no currency on the list. You can add them with the button down below.",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.secondary,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }

            if (state.isLoading || swipeRefreshState.isRefreshing) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            SettingsDialog(
                openSettingsDialog = showSettingsDialog,
                onDismissRequest = { showSettingsDialog = false }
            )

            AddToWatchlistDialog(
                showCreateWatchlistItemDialog = showAddToWatchlistDialog,
                onDismissRequest = { showAddToWatchlistDialog = false },
                onPositiveButtonClick = { baseCurrency, targetCurrency ->
                    viewModel.insertCurrency(
                        CurrencyWatchlistItemData(
                            uid = UUID.randomUUID().toString(),
                            baseCurrencyCode = baseCurrency,
                            targetCurrencyCode = targetCurrency,
                            rate = Random.nextDouble(0.0, 20.0),
                            previousChangeRate = Random.nextDouble(0.0, 0.5),
                            currentChangeRate = Random.nextDouble(0.0, 0.5),
                            date = "18.09.2022",
                        )
                    )
                },
                currencyList = listOf(
                    Symbol("ABC", ""),
                    Symbol("DEF", ""),
                    Symbol("GHI", ""),
                    Symbol("JKL", ""),
                    Symbol("MNO", ""),
                    Symbol("PRS", ""),
                    Symbol("TUV", ""),
                    Symbol("WYZ", ""),
                )
            )

            DeleteWatchlistItemDialog(
                showDeleteWatchlistItemDialog = showDeleteWatchlistItemDialog,
                onDismissRequest = {
                    showDeleteWatchlistItemDialog = false
                },
                onPositiveButtonClick = {
                    viewModel.deleteCurrency(deleteItem.uid)
                },
                onNegativeButtonClick = { showDeleteWatchlistItemDialog = false },
                baseCurrency = deleteItem.baseCurrencyCode ?: "",
                targetCurrency = deleteItem.targetCurrencyCode ?: ""
            )

            CurminErrorDialog(
                showErrorDialog = showErrorDialog,
                onDismissRequest = {
                    showErrorDialog = false
                },
                onPositiveButtonClick = {
                    showErrorDialog = false
                },
                errorMessage = state.error
            )
        }
    }
}