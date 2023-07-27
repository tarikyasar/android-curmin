package com.tarikyasar.curmin.presentation.ui.screens.currency_detail.composable.date

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.tarikyasar.curmin.presentation.composable.CurminDateDropdown

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrencyDateDropdown(
    onDateSelect: (startDate: String, endDate: String) -> Unit
) {
    var dateSelectionDropdownExpanded by remember { mutableStateOf(false) }
    var extraText by remember { mutableStateOf("") }
    var dateState by remember { mutableStateOf(DateSelection.LAST_WEEK) }

    CurminDateDropdown(
        expanded = dateSelectionDropdownExpanded,
        onExpandedChangeRequest = { dateSelectionDropdownExpanded = it },
        selectedItemText = dateState.getDateSelectionName(),
        extraText = extraText
    ) {
        DateSelection.values().forEach { date ->
            DropdownMenuItem(
                onClick = {
                    dateState = date
                    dateSelectionDropdownExpanded = false

                    extraText = dateState.getDateNames()

                    onDateSelect(
                        dateState.getDateRanges().first,
                        dateState.getDateRanges().second
                    )
                }
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = date.getDateSelectionName(),
                        fontSize = 20.sp
                    )

                    Text(
                        text = date.getDateNames(),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}