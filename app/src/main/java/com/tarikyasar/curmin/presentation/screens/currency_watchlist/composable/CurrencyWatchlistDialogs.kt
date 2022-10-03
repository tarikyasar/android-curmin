package com.tarikyasar.curmin.presentation.screens.currency_watchlist.composable

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.tarikyasar.curmin.R
import com.tarikyasar.curmin.domain.model.Symbol
import com.tarikyasar.curmin.presentation.composable.CurminDialog
import com.tarikyasar.curmin.presentation.composable.CurminDropdown
import com.tarikyasar.curmin.presentation.ui.theme.DialogWarningColor

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
    val context = LocalContext.current

    if (showCreateWatchlistItemDialog) {
        CurminDialog(
            onDismissRequest = onDismissRequest,
        ) {
            Surface(
                modifier = Modifier
                    .height(230.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Scaffold(
                    topBar = {
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
                                        onDismissRequest()
                                    }
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

                        Divider()


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
                            shape = RoundedCornerShape(10.dp),
                            elevation = ButtonDefaults.elevation(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary,
                                disabledBackgroundColor = MaterialTheme.colors.secondaryVariant
                            ),
                            modifier = Modifier.padding(bottom = 10.dp)
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
    properties: DialogProperties = DialogProperties()
) {
    if (showDeleteWatchlistItemDialog) {
        CurminDialog(
            onDismissRequest = onDismissRequest,
            properties = properties
        ) {
            Surface(
                modifier = Modifier
                    .height(310.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Scaffold(
                    topBar = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_warning),
                                contentDescription = "Dialog icon warning",
                                tint = DialogWarningColor,
                                modifier = Modifier
                                    .size(120.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }

                    },
                    modifier = Modifier
                        .background(
                            MaterialTheme.colors.surface,
                            RoundedCornerShape(10.dp)
                        )
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(
                            text = "Watchlist item containing $baseCurrency-$targetCurrency currencies will be deleted.\nAre you sure?",
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                        )

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Button(
                                onClick = {
                                    onPositiveButtonClick()
                                    onDismissRequest()
                                },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .padding(end = 5.dp, start = 10.dp)
                                    .weight(1f),
                                elevation = ButtonDefaults.elevation(),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.primary,
                                ),
                            ) {
                                Text(
                                    text = "Yes",
                                    fontSize = 24.sp,
                                    color = MaterialTheme.colors.onPrimary
                                )
                            }

                            Button(
                                onClick = {
                                    onNegativeButtonClick()
                                    onDismissRequest()
                                },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .padding(start = 5.dp, end = 10.dp)
                                    .weight(1f),
                                elevation = ButtonDefaults.elevation(),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.surface,
                                ),
                            ) {
                                Text(
                                    text = "No",
                                    fontSize = 24.sp,
                                    color = MaterialTheme.colors.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}