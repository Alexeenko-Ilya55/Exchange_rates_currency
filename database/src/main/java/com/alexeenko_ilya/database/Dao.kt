package com.alexeenko_ilya.database

import androidx.room.*
import androidx.room.Dao
import com.alexeenko_ilya.database.models.CurrencyEntity
import com.alexeenko_ilya.database.models.TABLE_NAME

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorites(currency: CurrencyEntity)

    @Query("SELECT * FROM $TABLE_NAME")
    fun getFavoritesCurrencies(): List<CurrencyEntity>

    @Delete
    suspend fun deleteFromFavorites(currency: CurrencyEntity)
}