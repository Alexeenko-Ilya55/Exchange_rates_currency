package com.alexeenko_ilya.data

import com.alexeenko_ilya.data.models.CurrencyDTO
import com.alexeenko_ilya.data.models.ExchangeRatesDTO
import kotlinx.coroutines.flow.Flow

interface ApiRepository {
    suspend fun getCurrencies(): Flow<List<CurrencyDTO>>
    suspend fun getExchangeRates(baseCurrency: String): Flow<ExchangeRatesDTO>
    suspend fun getExchangeRates(baseCurrency: String, toCurrencies: String): Flow<ExchangeRatesDTO>
}