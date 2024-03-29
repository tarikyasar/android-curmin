package com.tarikyasar.curmin.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tarikyasar.curmin.domain.database.model.CurrencyWatchlistItemData

@Dao
interface CurrencyWatchlistItemDao {
    @Query("SELECT * FROM currencywatchlistitemdata")
    suspend fun getAll(): List<CurrencyWatchlistItemData>

    @Query("SELECT * FROM currencywatchlistitemdata WHERE uid = :currencyWatchlistItemUid")
    suspend fun getCurrencyWatchlistItem(currencyWatchlistItemUid: String): CurrencyWatchlistItemData

    @Insert
    suspend fun insert(vararg currencies: CurrencyWatchlistItemData)

    @Update
    suspend fun updateCurrencyWatchlistItem(currencyWatchlistItemData: CurrencyWatchlistItemData)

    @Query("DELETE FROM currencywatchlistitemdata WHERE uid = :currencyWatchlistItemUid")
    suspend fun delete(currencyWatchlistItemUid: String)
}