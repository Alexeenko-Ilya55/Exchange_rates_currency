package com.alexeenko_ilya.data

import com.alexeenko_ilya.data.models.CurrencyDTO

interface Database {
    suspend fun addCurrencyToFavorites(currencyDTO: CurrencyDTO)
    suspend fun deleteCurrencyFromFavorites(currencyDTO: CurrencyDTO)
    suspend fun getFavoritesCurrencies(): List<CurrencyDTO>
}