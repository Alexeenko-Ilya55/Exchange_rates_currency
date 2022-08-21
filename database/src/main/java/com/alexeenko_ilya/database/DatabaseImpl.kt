package com.alexeenko_ilya.database

import com.alexeenko_ilya.data.Database
import com.alexeenko_ilya.data.models.CurrencyDTO
import com.alexeenko_ilya.database.models.CurrencyEntity
import javax.inject.Inject

class DatabaseImpl @Inject constructor(private val dao: Dao) : Database {

    override suspend fun addCurrencyToFavorites(currencyDTO: CurrencyDTO) = dao.addToFavorites(
        CurrencyEntity(currencyDTO.code)
    )

    override suspend fun deleteCurrencyFromFavorites(currencyDTO: CurrencyDTO) =
        dao.deleteFromFavorites(CurrencyEntity(currencyDTO.code))

    override suspend fun getFavoritesCurrencies() =
        dao.getFavoritesCurrencies().map { it.toCurrencyDTO() }

}