package com.tarikyasar.curmin.presentation.settings_dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarikyasar.curmin.R
import com.tarikyasar.curmin.presentation.composable.CurminDialog
import com.tarikyasar.curmin.presentation.composable.CurminDropdown
import com.tarikyasar.curmin.presentation.settings_dialog.model.Themes

@Composable
fun SettingsDialog(
    openSettingsDialog: Boolean,
    onDismissRequest: () -> Unit
) {
    if (openSettingsDialog) {
        CurminDialog(
            onDismissRequest = onDismissRequest,
        ) {
            Surface(
                modifier = Modifier
                    .padding(vertical = 80.dp),
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
                            Text(
                                text = "Settings",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = MaterialTheme.colors.onSurface
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
                    }
                ) {
                    Column {
                        ThemeSetting()
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeSetting() {
    var expanded by remember { mutableStateOf(false) }
    var themeState by remember { mutableStateOf(Themes.LIGHT) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Theme", fontSize = 20.sp)

        CurminDropdown(
            expanded = expanded,
            onExpandedChangeRequest = { expanded = it },
            selectedItemText = themeState.getThemeName()
        ) {
            Themes.values().forEach { theme ->
                DropdownMenuItem(onClick = {
                    themeState = theme
                    expanded = false
                }) {
                    Text(text = theme.getThemeName())
                }
            }
        }
    }
}