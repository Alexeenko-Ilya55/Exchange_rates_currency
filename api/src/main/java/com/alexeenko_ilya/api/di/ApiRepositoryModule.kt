package com.alexeenko_ilya.api.di


import com.alexeenko_ilya.api.ApiRepositoryImpl
import com.alexeenko_ilya.api.ApiService
import com.alexeenko_ilya.data.ApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.fastforex.io/"

@Module
@InstallIn(SingletonComponent::class)
class ApiRepositoryModule {

    @Provides
    @Singleton
    fun provideApiRepository(apiService: ApiService): ApiRepository =
        ApiRepositoryImpl(apiService = apiService)

    @Provides
    @Singleton
    fun provideApiService(): ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ApiService::class.java)
}