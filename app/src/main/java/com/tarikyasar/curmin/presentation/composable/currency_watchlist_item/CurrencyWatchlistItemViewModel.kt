package com.tarikyasar.curmin.presentation.composable.currency_watchlist_item

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyWatchlistItemViewModel @Inject constructor(
) : ViewModel() {

    private val _state = mutableStateOf(CurrencyWatchlistItemState())
    val state: State<CurrencyWatchlistItemState> = _state

    init {
    }

}