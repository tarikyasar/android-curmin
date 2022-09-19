package com.tarikyasar.curmin.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData

@Dao
interface CurrencyWatchlistItemDao {
    @Query("SELECT * FROM currencywatchlistitemdata")
    suspend fun getAll(): List<CurrencyWatchlistItemData>

    @Query("SELECT * FROM currencywatchlistitemdata WHERE uid = :currencyWatchlistItemUid")
    suspend fun getCurrencyWatchlistItem(currencyWatchlistItemUid: String): CurrencyWatchlistItemData

    @Insert
    suspend fun insert(vararg currencies: CurrencyWatchlistItemData)

    @Query("DELETE FROM currencywatchlistitemdata WHERE uid = :currencyWatchlistItemUid")
    suspend fun delete(currencyWatchlistItemUid: String)
}