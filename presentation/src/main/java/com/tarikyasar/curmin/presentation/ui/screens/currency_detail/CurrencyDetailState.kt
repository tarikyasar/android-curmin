package com.tarikyasar.curmin.presentation.ui.screens.currency_detail

import com.tarikyasar.curmin.domain.model.CurminError

data class CurrencyDetailState(
    val error: CurminError? = null,
    val isLoading: Boolean = false,
    val currencyRates: List<Pair<Int, Double>>? = emptyList()
)