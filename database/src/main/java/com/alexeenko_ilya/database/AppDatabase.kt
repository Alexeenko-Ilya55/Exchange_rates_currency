package com.alexeenko_ilya.database

import android.content.Context
import androidx.room.Database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.alexeenko_ilya.database.models.CurrencyEntity

@Database(entities = [CurrencyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun Dao(): Dao

    companion object {
        fun buildsDatabase(context: Context, dbName: String): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()
        }
    }
}