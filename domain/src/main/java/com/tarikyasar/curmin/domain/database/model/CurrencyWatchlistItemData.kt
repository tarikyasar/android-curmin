package com.tarikyasar.curmin.domain.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class CurrencyWatchlistItemData(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "base_currency_code") val baseCurrencyCode: String?,
    @ColumnInfo(name = "target_currency_code") val targetCurrencyCode: String?,
    @ColumnInfo(name = "rate") val rate: Double?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "change") val change: Double?
) : Serializable