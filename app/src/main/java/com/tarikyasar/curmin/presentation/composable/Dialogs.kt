@file:OptIn(ExperimentalComposeUiApi::class)

package com.tarikyasar.curmin.presentation.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tarikyasar.curmin.presentation.ui.theme.*

@Composable
fun CurminDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(vertical = 80.dp)
    ) {
        Dialog(
            onDismissRequest,
            properties.let {
                DialogProperties(
                    dismissOnBackPress = it.dismissOnBackPress,
                    dismissOnClickOutside = it.dismissOnClickOutside,
                    securePolicy = it.securePolicy,
                    usePlatformDefaultWidth = false
                )
            },
            content = {
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    content = content
                )
            }
        )
    }
}

@Composable
fun CurminWarningDialog(
    showWarningDialog: Boolean,
    onDismissRequest: () -> Unit,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit,
    warningMessage: String,
    properties: DialogProperties = DialogProperties()
) {
    if (showWarningDialog) {
        CurminDialog(
            onDismissRequest = onDismissRequest,
            properties = properties
        ) {
            Surface(
                border = BorderStroke(1.dp, DialogWarningBorderColor),
                modifier = Modifier
                    .padding(vertical = 10.dp)
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
                                text = "Warning",
                                color = MaterialTheme.colors.onSurface,
                                fontSize = 24.sp,
                                modifier = Modifier.padding(10.dp),
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
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = warningMessage,
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    onPositiveButtonClick()
                                    onDismissRequest()
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = PositiveButtonBackgroundColor),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "Yes",
                                    fontSize = 24.sp,
                                    color = PositiveButtonTextColor
                                )
                            }

                            Button(
                                onClick = {
                                    onNegativeButtonClick()
                                    onDismissRequest()
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = NegativeButtonBackgroundColor),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "No",
                                    fontSize = 24.sp,
                                    color = NegativeButtonTextColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurminErrorDialog(
    showErrorDialog: Boolean,
    onDismissRequest: () -> Unit,
    onPositiveButtonClick: () -> Unit,
    warningMessage: String,
    properties: DialogProperties = DialogProperties()
) {
    if (showErrorDialog) {
        CurminDialog(
            onDismissRequest = onDismissRequest,
            properties = properties
        ) {
            Surface(
                border = BorderStroke(1.dp, DialogErrorBorderColor),
                modifier = Modifier
                    .padding(vertical = 10.dp)
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
                                text = "Warning",
                                color = MaterialTheme.colors.onSurface,
                                fontSize = 24.sp,
                                modifier = Modifier.padding(10.dp),
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
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = warningMessage,
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    onPositiveButtonClick()
                                    onDismissRequest()
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = PositiveButtonBackgroundColor),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "Yes",
                                    fontSize = 24.sp,
                                    color = PositiveButtonTextColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
