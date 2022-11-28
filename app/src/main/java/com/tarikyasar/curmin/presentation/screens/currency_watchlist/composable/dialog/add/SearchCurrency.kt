package com.tarikyasar.curmin.presentation.screens.currency_watchlist.composable.dialog.add

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarikyasar.curmin.R
import com.tarikyasar.curmin.domain.model.Symbol

@Composable
fun CurrencySearch(
    title: String,
    currencyList: List<Symbol>,
    onBackButtonClick: () -> Unit,
    onSelect: (currencyCode: String) -> Unit
) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    var isSearchViewVisible by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxHeight(0.62F),
        shape = RoundedCornerShape(10.dp)
    ) {
        Scaffold(
            topBar = {
                CurrencySearchViewTopBar(
                    title = title,
                    isSearchViewVisible = isSearchViewVisible,
                    onBackButtonClick = {
                        onBackButtonClick()
                    },
                    onSearchButtonClick = {
                        isSearchViewVisible = isSearchViewVisible.not()
                    }
                )
            }
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                AnimatedVisibility(visible = isSearchViewVisible) {
                    SearchView(
                        searchText = searchText.text,
                        onTextChanged = {
                            searchText = searchText.copy(it)
                        }
                    )
                }

                LazyColumn(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxHeight()
                        .animateContentSize()
                ) {
                    items(currencyList.filter {
                        it.hasText(searchText.text)
                    }) { currency ->
                        CurrencySearchItem(currency = currency) { currencyCode ->
                            onSelect(currencyCode)
                            onBackButtonClick()
                        }

                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
fun CurrencySearchViewTopBar(
    title: String,
    isSearchViewVisible: Boolean,
    onBackButtonClick: () -> Unit,
    onSearchButtonClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = null,
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .clickable {
                    onBackButtonClick()
                }
        )

        Text(
            text = title,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterVertically),
        )

        Icon(
            imageVector = if (isSearchViewVisible) Icons.Default.Close else Icons.Default.Search,
            contentDescription = null,
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    onSearchButtonClick()
                }
        )
    }
}

@Composable
fun SearchView(
    searchText: String,
    onTextChanged: (String) -> Unit
) {
    TextField(
        value = searchText,
        onValueChange = { value ->
            onTextChanged(value)
        },
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        trailingIcon = {
            if (searchText != "") {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                        .clickable {
                            onTextChanged("")
                        }
                )
            }
        },
        singleLine = true,
        placeholder = { Text(stringResource(id = R.string.search_currency)) },
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onSurface,
            cursorColor = MaterialTheme.colors.onSurface,
            leadingIconColor = MaterialTheme.colors.onSurface,
            trailingIconColor = MaterialTheme.colors.onSurface,
            backgroundColor = MaterialTheme.colors.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun CurrencySearchItem(
    currency: Symbol,
    onClick: (currencyCode: String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable {
                onClick(currency.code)
            },
    ) {
        Text(
            text = "${currency.code}: ${currency.name}",
            fontSize = 20.sp,
            textAlign = TextAlign.Start
        )
    }
}
