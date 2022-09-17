package com.tarikyasar.curmin.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItem

@Dao
interface CurrencyWatchlistItemDao {
    @Query("SELECT * FROM currencywatchlistitem")
    suspend fun getAll(): List<CurrencyWatchlistItem>

    @Insert
    suspend fun insert(vararg currencies: CurrencyWatchlistItem)

    @Query("DELETE FROM currencywatchlistitem WHERE uid = :currencyWatchlistItemUid")
    suspend fun delete(currencyWatchlistItemUid: Int)
}