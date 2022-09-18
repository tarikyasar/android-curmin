package com.tarikyasar.curmin.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class CurrencyWatchlistItemData(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "base_currency_code") val baseCurrencyCode: String?,
    @ColumnInfo(name = "target_currency_code") val targetCurrencyCode: String?,
    @ColumnInfo(name = "rate") val rate: Double?,
    @ColumnInfo(name = "previous_change_rate") val previousChangeRate: Double?,
    @ColumnInfo(name = "current_change_rate") val currentChangeRate: Double?,
    @ColumnInfo(name = "date") val date: String?,
)