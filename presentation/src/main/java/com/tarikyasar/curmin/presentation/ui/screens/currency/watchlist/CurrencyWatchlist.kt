package com.tarikyasar.curmin.presentation.ui.screens.currency.watchlist

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.presentation.R
import com.tarikyasar.curmin.presentation.composable.CurminErrorDialog
import com.tarikyasar.curmin.presentation.composable.EmptyComposable
import com.tarikyasar.curmin.presentation.composable.SwipeableCurrencyWatchlistItem
import com.tarikyasar.curmin.presentation.ui.base.curminViewModel
import com.tarikyasar.curmin.presentation.ui.screens.currency.watchlist.CurrencyWatchlistContract.*
import com.tarikyasar.curmin.presentation.ui.screens.currency.watchlist.composable.CurrencyWatchlistTopBar
import com.tarikyasar.curmin.presentation.ui.screens.currency.watchlist.composable.dialog.DeleteWatchlistItemDialog
import com.tarikyasar.curmin.presentation.ui.screens.currency.watchlist.composable.dialog.add.AddToWatchlistDialog
import com.tarikyasar.curmin.presentation.ui.screens.settings_dialog.SettingsDialog
import com.tarikyasar.curmin.presentation.ui.theme.CurrencyDownColor

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CurrencyWatchlist(
    viewModel: CurrencyWatchlistViewModel = curminViewModel(),
    onNavigateToCurrencyDetail: (currency: CurrencyWatchlistItemData) -> Unit
) {
    val (uiState, onIntent, _) = viewModel
    var showDeleteWatchlistItemDialog by remember { mutableStateOf(false) }
    var showAddToWatchlistDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    val swipeRefreshState by remember { mutableStateOf(SwipeRefreshState(false)) }
    var deleteItem by remember {
        mutableStateOf(
            CurrencyWatchlistItemData(
                "",
                null,
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
        ) {
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.TopCenter
            ) {
                CurrencyWatchlistContent(
                    uiState = uiState,
                    onIntent = onIntent,
                    onDelete = {
                        deleteItem = it

                        if (uiState.askToRemoveItemParameter == true) {
                            showDeleteWatchlistItemDialog = true
                        } else {
                            onIntent(Intent.DeleteCurrencyWatchlistItem(deleteItem.uid))
                        }
                    },
                    swipeRefreshState = swipeRefreshState,
                    onNavigateToCurrencyDetail = { currency ->
                        onNavigateToCurrencyDetail(currency)
                    },
                    onAddItemClick = { showAddToWatchlistDialog = true }
                )

                SettingsDialog(
                    showSettingsDialog = showSettingsDialog,
                    onDismissRequest = {
                        showSettingsDialog = false
                        onIntent(Intent.GetAskToRemoveItemParameter)
                    }
                )

                AddToWatchlistDialog(
                    showCreateWatchlistItemDialog = showAddToWatchlistDialog,
                    onDismissRequest = { showAddToWatchlistDialog = false },
                    onPositiveButtonClick = { baseCurrency, targetCurrency ->
                        onIntent(
                            Intent.CreateCurrencyWatchlistItem(
                                baseCurrencyCode = baseCurrency,
                                targetCurrencyCode = targetCurrency
                            )
                        )
                    },
                    currencyList = uiState.symbols
                )

                DeleteWatchlistItemDialog(
                    showDeleteWatchlistItemDialog = showDeleteWatchlistItemDialog,
                    onDismissRequest = {
                        showDeleteWatchlistItemDialog = false
                    },
                    onPositiveButtonClick = {
                        onIntent(Intent.DeleteCurrencyWatchlistItem(deleteItem.uid))
                    },
                    onNegativeButtonClick = { showDeleteWatchlistItemDialog = false },
                    baseCurrency = deleteItem.baseCurrencyCode ?: "",
                    targetCurrency = deleteItem.targetCurrencyCode ?: ""
                )

                CurminErrorDialog(
                    showErrorDialog = uiState.error != null,
                    onDismissRequest = {
                        viewModel.resetError()
                    },
                    onPositiveButtonClick = {
                        viewModel.resetError()
                    },
                    errorMessage = uiState.error?.getErrorMessage() ?: ""
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrencyWatchlistContent(
    uiState: UiState,
    onIntent: (Intent) -> Unit,
    onDelete: (currency: CurrencyWatchlistItemData) -> Unit,
    swipeRefreshState: SwipeRefreshState,
    onNavigateToCurrencyDetail: (currency: CurrencyWatchlistItemData) -> Unit,
    onAddItemClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxHeight()
    ) {
        if (uiState.currencies.isNotEmpty()) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    onIntent(Intent.GetCurrencies(true))
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
                    items(uiState.currencies) { currency ->
                        SwipeableCurrencyWatchlistItem(
                            base = currency.baseCurrencyCode ?: "",
                            target = currency.targetCurrencyCode ?: "",
                            value = currency.rate ?: 0.0,
                            change = currency.change ?: 0.0,
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
        } else {
            EmptyComposable(
                text = stringResource(id = R.string.empty_watchlist),
                iconPainter = painterResource(id = R.drawable.ic_empty),
                buttonText = stringResource(id = R.string.add_to_watchlist),
                onButtonClick = {
                    onAddItemClick()
                }
            )
        }
    }
}