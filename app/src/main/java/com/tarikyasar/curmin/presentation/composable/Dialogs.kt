package com.tarikyasar.curmin.presentation.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tarikyasar.curmin.R
import com.tarikyasar.curmin.presentation.ui.theme.DialogErrorColor
import com.tarikyasar.curmin.presentation.ui.theme.DialogWarningColor

@OptIn(ExperimentalComposeUiApi::class)
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
                                    .align(CenterHorizontally)
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
                            text = warningMessage,
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
                        )

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CurminErrorDialog(
    showErrorDialog: Boolean,
    onDismissRequest: () -> Unit,
    onPositiveButtonClick: () -> Unit,
    errorMessage: String,
    properties: DialogProperties = DialogProperties()
) {
    if (showErrorDialog) {
        CurminDialog(
            onDismissRequest = onDismissRequest,
            properties = properties
        ) {
            Surface(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .height(320.dp),
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
                                painter = painterResource(id = R.drawable.ic_error),
                                contentDescription = "Dialog icon error",
                                tint = DialogErrorColor,
                                modifier = Modifier
                                    .size(120.dp)
                                    .align(CenterHorizontally),
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
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = errorMessage,
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                                .align(CenterHorizontally)
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
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.okay),
                                    fontSize = 24.sp,
                                    color = MaterialTheme.colors.onPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
