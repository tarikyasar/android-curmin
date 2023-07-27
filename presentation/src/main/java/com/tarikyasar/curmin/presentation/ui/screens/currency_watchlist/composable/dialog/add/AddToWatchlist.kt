package com.tarikyasar.curmin.presentation.ui.screens.currency_watchlist.composable.dialog.add

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarikyasar.curmin.presentation.R
import com.tarikyasar.curmin.domain.model.Symbol
import com.tarikyasar.curmin.presentation.composable.CurminDialog

@Composable
fun AddToWatchlistDialog(
    showCreateWatchlistItemDialog: Boolean,
    onDismissRequest: () -> Unit,
    onPositiveButtonClick: (baseCurrency: String, targetCurrency: String) -> Unit,
    currencyList: List<Symbol>
) {
    var baseCurrencyState by remember { mutableStateOf("AED") }
    var targetCurrencyState by remember { mutableStateOf("AED") }
    var dialogState by remember { mutableStateOf(WatchlistDialogState.ADD_TO_WATCHLIST_SCREEN) }
    var currencyState by remember { mutableStateOf(CurrencyState.BASE_CURRENCY) }

    if (showCreateWatchlistItemDialog) {
        CurminDialog(
            onDismissRequest = {
                dialogState = WatchlistDialogState.ADD_TO_WATCHLIST_SCREEN
                onDismissRequest()
            },
        ) {
            when (dialogState) {
                WatchlistDialogState.ADD_TO_WATCHLIST_SCREEN -> {
                    AddToWatchlistContent(
                        baseCurrencyState = baseCurrencyState,
                        targetCurrencyState = targetCurrencyState,
                        onCloseButtonClick = {
                            dialogState.reset()
                            onDismissRequest()
                        },
                        onBaseCurrencyClick = {
                            dialogState = WatchlistDialogState.CURRENCY_SEARCH_SCREEN
                            currencyState = CurrencyState.BASE_CURRENCY
                        },
                        onTargetCurrencyClick = {
                            dialogState = WatchlistDialogState.CURRENCY_SEARCH_SCREEN
                            currencyState = CurrencyState.TARGET_CURRENCY
                        },
                        onButtonClick = {
                            onPositiveButtonClick(
                                baseCurrencyState,
                                targetCurrencyState
                            )
                            baseCurrencyState = currencyList[0].code
                            targetCurrencyState = currencyList[0].code
                            onDismissRequest()
                        }
                    )
                }
                WatchlistDialogState.CURRENCY_SEARCH_SCREEN -> {
                    CurrencySearch(
                        title = currencyState.value,
                        currencyList = currencyList,
                        onBackButtonClick = {
                            dialogState = WatchlistDialogState.ADD_TO_WATCHLIST_SCREEN
                        },
                        onSelect = { currencyCode ->
                            when (currencyState) {
                                CurrencyState.BASE_CURRENCY -> {
                                    baseCurrencyState = currencyCode
                                }
                                CurrencyState.TARGET_CURRENCY -> {
                                    targetCurrencyState = currencyCode
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AddToWatchlistContent(
    baseCurrencyState: String,
    targetCurrencyState: String,
    onCloseButtonClick: () -> Unit,
    onBaseCurrencyClick: () -> Unit,
    onTargetCurrencyClick: () -> Unit,
    onButtonClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .height(240.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Scaffold(
            topBar = {
                AddToWatchlistTopBar(onCloseButtonClick = onCloseButtonClick)
            },
            modifier = Modifier.background(
                MaterialTheme.colors.surface,
                RoundedCornerShape(10.dp)
            )
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                CurrencyItem(
                    title = stringResource(id = R.string.base_currency),
                    currencyCode = baseCurrencyState
                ) {
                    onBaseCurrencyClick()
                }

                Divider()

                CurrencyItem(
                    title = stringResource(id = R.string.target_currency),
                    currencyCode = targetCurrencyState
                ) {
                    onTargetCurrencyClick()
                }

                Button(
                    onClick = {
                        onButtonClick()
                    },
                    enabled = baseCurrencyState != targetCurrencyState && baseCurrencyState != "" && targetCurrencyState != "",
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.elevation(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        disabledBackgroundColor = MaterialTheme.colors.secondaryVariant
                    ),
                    modifier = Modifier
                        .height(62.dp)
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.add_to_watchlist),
                        fontSize = 22.sp,
                        color = if (baseCurrencyState != targetCurrencyState && baseCurrencyState != "" && targetCurrencyState != "") MaterialTheme.colors.onPrimary else Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun AddToWatchlistTopBar(
    onCloseButtonClick: () -> Unit
) {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .clickable {
                    Toast
                        .makeText(
                            context,
                            "Base and target currency must be different from each other.",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    onCloseButtonClick()
                }
        )
    }
}

@Composable
fun CurrencyItem(
    title: String,
    currencyCode: String,
    onClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 10.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colors.surface)
                .clickable {
                    onClick()
                }
                .padding(8.dp)
        ) {
            Text(text = currencyCode)

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                contentDescription = "",
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

enum class WatchlistDialogState {
    ADD_TO_WATCHLIST_SCREEN,
    CURRENCY_SEARCH_SCREEN;

    fun reset() {
        ADD_TO_WATCHLIST_SCREEN
    }
}

enum class CurrencyState(val value: String) {
    BASE_CURRENCY(value = "Base Currency"),
    TARGET_CURRENCY(value = "Target Currency")
}
