package com.alexeenko_ilya.database.di

import android.content.Context
import com.alexeenko_ilya.data.Database

import com.alexeenko_ilya.database.AppDatabase
import com.alexeenko_ilya.database.Dao
import com.alexeenko_ilya.database.DatabaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "Currencies"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(dao: Dao): Database =
        DatabaseImpl(dao)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): Dao =
        AppDatabase.buildsDatabase(context, DATABASE_NAME).Dao()
}