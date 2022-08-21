package com.alexeenko_ilya.data.di

import com.alexeenko_ilya.data.ApiRepository
import com.alexeenko_ilya.data.Database
import com.alexeenko_ilya.data.RepositoryImpl
import com.alexeenko_ilya.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideRepository(apiRepository: ApiRepository, database: Database): Repository =
        RepositoryImpl(apiRepository = apiRepository, database = database)
}
