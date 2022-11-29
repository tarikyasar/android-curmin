package com.tarikyasar.curmin.presentation.screens.currency_watchlist

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.tarikyasar.curmin.R
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.presentation.composable.CurminErrorDialog
import com.tarikyasar.curmin.presentation.composable.SwipeableCurrencyWatchlistItem
import com.tarikyasar.curmin.presentation.screens.currency_watchlist.composable.CurrencyWatchlistTopBar
import com.tarikyasar.curmin.presentation.screens.currency_watchlist.composable.DeleteWatchlistItemDialog
import com.tarikyasar.curmin.presentation.screens.currency_watchlist.composable.dialog.add.AddToWatchlistDialog
import com.tarikyasar.curmin.presentation.screens.settings_dialog.SettingsDialog
import com.tarikyasar.curmin.presentation.ui.theme.CurrencyDownColor
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@Composable
fun CurrencyWatchlist(
    viewModel: CurrencyWatchlistViewModel = hiltViewModel(),
    onNavigateToCurrencyDetail: (currency: CurrencyWatchlistItemData) -> Unit
) {
    val state = viewModel.state.value
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
                null
            )
        )
    }

    Box {
        Scaffold(
            topBar = {
                CurrencyWatchlistTopBar(
                    onAddButtonClick = { showAddToWatchlistDialog = true },
                    onSettingsButtonClick = { showSettingsDialog = true },
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .blur(if (state.isLoading) 50.dp else 0.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.TopCenter
            ) {
                CurrencyWatchlistContent(
                    currencies = state.currencies,
                    getCurrencies = { viewModel.getCurrencies(true) },
                    onDelete = {
                        deleteItem = it

                        if (state.askToRemoveItemParameter == true) {
                            showDeleteWatchlistItemDialog = true
                        } else {
                            viewModel.deleteCurrency(deleteItem.uid)
                        }
                    },
                    isLoading = state.isLoading,
                    swipeRefreshState = swipeRefreshState,
                    onNavigateToCurrencyDetail = { currency ->
                        onNavigateToCurrencyDetail(currency)
                    }
                )

                SettingsDialog(
                    showSettingsDialog = showSettingsDialog,
                    onDismissRequest = {
                        showSettingsDialog = false
                        viewModel.getAskToRemoveItemParameter()
                    }
                )

                AddToWatchlistDialog(
                    showCreateWatchlistItemDialog = showAddToWatchlistDialog,
                    onDismissRequest = { showAddToWatchlistDialog = false },
                    onPositiveButtonClick = { baseCurrency, targetCurrency ->
                        viewModel.createCurrencyWatchlisttem(
                            baseCurrencyCode = baseCurrency,
                            targetCurrencyCode = targetCurrency
                        )
                    },
                    currencyList = state.symbols
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

        if (state.isLoading == true) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrencyWatchlistContent(
    currencies: List<CurrencyWatchlistItemData>,
    getCurrencies: () -> Unit,
    onDelete: (currency: CurrencyWatchlistItemData) -> Unit,
    isLoading: Boolean,
    swipeRefreshState: SwipeRefreshState,
    onNavigateToCurrencyDetail: (currency: CurrencyWatchlistItemData) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxHeight()
    ) {
        if (currencies.isNotEmpty()) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    getCurrencies()
                }
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxHeight()
                        .animateContentSize()
                ) {
                    items(currencies) { currency ->
                        SwipeableCurrencyWatchlistItem(
                            base = currency.baseCurrencyCode ?: "",
                            target = currency.targetCurrencyCode ?: "",
                            value = ((currency.rate
                                ?: 0.0) * 100.0).roundToInt() / 100.0,
                            change = (0.1 * 100.0).roundToInt() / 100.0,
                            date = currency.date ?: "",
                            onItemClick = {
                                onNavigateToCurrencyDetail(currency)
                            },
                            backgroundColor = CurrencyDownColor,
                            icon = painterResource(id = R.drawable.ic_delete),
                            onSwipeAction = {
                                onDelete(currency)
                            }
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        } else if (isLoading.not()) {
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
                    text = stringResource(id = R.string.empty_watchlist),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}