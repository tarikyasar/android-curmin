package com.tarikyasar.curmin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItem

@Database(entities = [CurrencyWatchlistItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyWatchlistItemDao
}