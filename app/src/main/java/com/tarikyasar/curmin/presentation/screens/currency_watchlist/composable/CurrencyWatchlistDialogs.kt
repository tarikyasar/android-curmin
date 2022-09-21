package com.tarikyasar.curmin.presentation.screens.currency_watchlist.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarikyasar.curmin.domain.model.Symbol
import com.tarikyasar.curmin.presentation.composable.CurminDialog
import com.tarikyasar.curmin.presentation.composable.CurminDropdown
import com.tarikyasar.curmin.presentation.composable.CurminWarningDialog

@Composable
fun AddToWatchlistDialog(
    showCreateWatchlistItemDialog: Boolean,
    onDismissRequest: () -> Unit,
    onPositiveButtonClick: (baseCurrency: String, targetCurrency: String) -> Unit,
    currencyList: List<Symbol>
) {
    var baseCurrencyListExpanded by remember { mutableStateOf(false) }
    var targetCurrencyListExpanded by remember { mutableStateOf(false) }
    var baseCurrencyState by remember { mutableStateOf(currencyList[0].code) }
    var targetCurrencyState by remember { mutableStateOf(currencyList[0].code) }

    if (showCreateWatchlistItemDialog) {
        CurminDialog(
            onDismissRequest = onDismissRequest,
        ) {
            Surface(
                modifier = Modifier
                    .height(260.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Scaffold(
                    topBar = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Select base and target currencies. Remember, they must be different from each other.",
                                color = MaterialTheme.colors.onSurface,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(vertical = 20.dp),
                                textAlign = TextAlign.Center
                            )
                        }
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
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 10.dp)
                        ) {
                            Text(text = "Base Currency", fontSize = 20.sp)

                            CurminDropdown(
                                expanded = baseCurrencyListExpanded,
                                onExpandedChangeRequest = {
                                    baseCurrencyListExpanded = it
                                },
                                selectedItemText = baseCurrencyState
                            ) {
                                currencyList.forEach { symbol ->
                                    DropdownMenuItem(onClick = {
                                        baseCurrencyState = symbol.code
                                        baseCurrencyListExpanded = false
                                    }) {
                                        Text(symbol.code)
                                    }
                                }
                            }
                        }

                        Divider()

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 10.dp)
                        ) {
                            Text(text = "Target Currency", fontSize = 20.sp)

                            CurminDropdown(
                                expanded = targetCurrencyListExpanded,
                                onExpandedChangeRequest = {
                                    targetCurrencyListExpanded = it
                                },
                                selectedItemText = targetCurrencyState
                            ) {
                                currencyList.forEach { symbol ->
                                    DropdownMenuItem(onClick = {
                                        targetCurrencyState = symbol.code
                                        targetCurrencyListExpanded = false
                                    }) {
                                        Text(symbol.code)
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = {
                                onPositiveButtonClick(
                                    baseCurrencyState,
                                    targetCurrencyState
                                )
                                baseCurrencyState = currencyList[0].code
                                targetCurrencyState = currencyList[0].code
                                onDismissRequest()
                            },
                            enabled = baseCurrencyState != targetCurrencyState,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary,
                                disabledBackgroundColor = MaterialTheme.colors.secondaryVariant
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .padding(vertical = 20.dp)
                                .background(
                                    if (baseCurrencyState != targetCurrencyState) MaterialTheme.colors.primary else MaterialTheme.colors.secondaryVariant,
                                    RoundedCornerShape(10.dp)
                                ),
                            elevation = ButtonDefaults.elevation()
                        ) {
                            Text(
                                text = "Add To Watchlist",
                                fontSize = 20.sp,
                                color = if (baseCurrencyState != targetCurrencyState) MaterialTheme.colors.onPrimary else Color.Gray
                            )
                        }
                    }
                }
            }
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