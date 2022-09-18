package com.tarikyasar.curmin.presentation.screens.settings_dialog

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.tarikyasar.curmin.R
import com.tarikyasar.curmin.domain.model.Themes
import com.tarikyasar.curmin.presentation.composable.CurminDialog
import com.tarikyasar.curmin.presentation.composable.CurminDropdown

@Composable
fun SettingsDialog(
    viewModel: SettingsDialogViewModel = hiltViewModel(),
    openSettingsDialog: Boolean,
    onDismissRequest: () -> Unit,
) {
    val state = viewModel.state.value

    if (openSettingsDialog) {
        CurminDialog(
            onDismissRequest = {
                viewModel.getTheme()
                onDismissRequest()
            }
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
                                        viewModel.getTheme()
                                    }
                            )
                        }
                    }
                ) {
                    Box {
                        Column {
                            ThemeSetting(
                                themes = state.themes,
                                onSelectTheme = { themes ->
                                    viewModel.setTheme(themes)
                                })

                            Divider()
                        }

                        if (state.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeSetting(
    themes: Themes?,
    onSelectTheme: (Themes) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var themeState by remember { mutableStateOf(themes) }

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
            selectedItemText = themeState?.getThemeName() ?: Themes.SYSTEM_THEME.getThemeName()
        ) {
            Themes.values().forEach { theme ->
                DropdownMenuItem(onClick = {
                    themeState = theme
                    expanded = false
                    onSelectTheme(theme)
                }) {
                    Text(text = theme.getThemeName())
                }
            }
        }
    }
}