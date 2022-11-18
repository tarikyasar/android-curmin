package com.tarikyasar.curmin.presentation.screens.currency_watchlist

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.tarikyasar.curmin.presentation.composable.CurrencyWatchlistItem
import com.tarikyasar.curmin.presentation.screens.currency_watchlist.composable.AddToWatchlistDialog
import com.tarikyasar.curmin.presentation.screens.currency_watchlist.composable.CurrencyWatchlistTopBar
import com.tarikyasar.curmin.presentation.screens.currency_watchlist.composable.DeleteWatchlistItemDialog
import com.tarikyasar.curmin.presentation.screens.settings_dialog.SettingsDialog
import com.tarikyasar.curmin.presentation.ui.theme.SwipeDeleteButtonBackgroundColor
import com.tarikyasar.curmin.utils.DateUtils
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import java.time.LocalDateTime
import java.util.*
import kotlin.math.roundToInt
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@Composable
fun CurrencyWatchlist(
    viewModel: CurrencyWatchlistViewModel = hiltViewModel(),
    onNavigateToCurrencyDetail: (baseCurrency: String, targetCurrency: String) -> Unit,
    isLoading: Boolean
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
                null,
                null,
                null
            )
        )
    }

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
                currencies = state.currencies,
                getCurrencies = { viewModel.getCurrencies() },
                onDelete = {
                    deleteItem = it

                    if (state.askToRemoveItemParameter == true) {
                        showDeleteWatchlistItemDialog = true
                    } else {
                        viewModel.deleteCurrency(deleteItem.uid)
                    }
                },
                isLoading = isLoading,
                swipeRefreshState = swipeRefreshState,
                onNavigateToCurrencyDetail = { baseCurrency, targetCurrency ->
                    onNavigateToCurrencyDetail(baseCurrency, targetCurrency)
                }
            )

            SettingsDialog(
                openSettingsDialog = showSettingsDialog,
                onDismissRequest = {
                    showSettingsDialog = false
                    viewModel.getAskToRemoveItemParameter()
                    viewModel.getCurrencies()
                }
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
                            date = DateUtils.formatTime(LocalDateTime.now()),
                        )
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
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrencyWatchlistContent(
    currencies: List<CurrencyWatchlistItemData>,
    getCurrencies: () -> Unit,
    onDelete: (currency: CurrencyWatchlistItemData) -> Unit,
    isLoading: Boolean,
    swipeRefreshState: SwipeRefreshState,
    onNavigateToCurrencyDetail: (baseCurrency: String, targetCurrency: String) -> Unit
) {
    var changeValue1 by remember { mutableStateOf(Random.nextDouble(-0.25, 0.25)) }

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
                    changeValue1 = Random.nextDouble(-0.5, 0.5)
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
                        key(currency.uid) {
                            val deleteSwipeAction = SwipeAction(
                                icon = painterResource(R.drawable.ic_delete),
                                background = SwipeDeleteButtonBackgroundColor,
                                onSwipe = {
                                    onDelete(currency)
                                }
                            )

                            SwipeableActionsBox(
                                endActions = listOf(deleteSwipeAction),
                                swipeThreshold = 60.dp,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MaterialTheme.colors.surface)
                            ) {
                                CurrencyWatchlistItem(
                                    base = currency.baseCurrencyCode ?: "",
                                    target = currency.targetCurrencyCode ?: "",
                                    value = ((currency.rate
                                        ?: 0.0) * 100.0).roundToInt() / 100.0,
                                    change = (changeValue1 * 100.0).roundToInt() / 100.0,
                                    date = DateUtils.formatTime(LocalDateTime.now()),
                                    onClick = {
                                        onNavigateToCurrencyDetail(
                                            currency.baseCurrencyCode!!,
                                            currency.targetCurrencyCode!!
                                        )
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                        }
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