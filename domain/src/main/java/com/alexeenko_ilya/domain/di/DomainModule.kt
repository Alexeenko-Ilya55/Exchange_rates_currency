package com.alexeenko_ilya.domain.di


import com.alexeenko_ilya.domain.Repository
import com.alexeenko_ilya.domain.useCases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideAddCurrencyToFavoritesUseCase(repository: Repository) =
        AddCurrencyToFavoritesUseCase(repository)

    @Provides
    fun provideDeleteCurrencyFromFavoritesUseCase(repository: Repository) =
        DeleteCurrencyFromFavoritesUseCase(repository)

    @Provides
    fun provideGetAllExchangeRatesUseCase(repository: Repository) =
        GetAllExchangeRatesUseCase(repository)

    @Provides
    fun provideGetExchangeRatesToFavoritesUseCase(repository: Repository) =
        GetExchangeRatesToFavoritesUseCase(repository)

    @Provides
    fun provideGetCurrenciesUseCase(repository: Repository) =
        GetCurrenciesUseCase(repository)
}