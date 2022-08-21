package com.alexeenko_ilya.domain

import com.alexeenko_ilya.domain.models.Currency
import com.alexeenko_ilya.domain.models.ExchangeRates
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getAllCurrencies(): Flow<List<Currency>>
    suspend fun getAllExchangeRates(baseCurrency: String): Flow<ExchangeRates>
    suspend fun getFavoritesExchangeRates(baseCurrency: String): Flow<ExchangeRates>
    suspend fun addCurrencyToFavorites(currency: Currency)
    suspend fun deleteCurrencyFromFavorites(currency: Currency)
}