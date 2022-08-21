package com.alexeenko_ilya.data

import com.alexeenko_ilya.data.models.CurrencyDTO
import com.alexeenko_ilya.domain.Repository
import com.alexeenko_ilya.domain.models.Currency
import com.alexeenko_ilya.domain.models.ExchangeRates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiRepository: ApiRepository,
    private val database: Database
) : Repository {

    override suspend fun getAllCurrencies(): Flow<List<Currency>> =
        apiRepository.getCurrencies().map {
            it.map { currencyDTO -> currencyDTO.toCurrency(false) }
        }

    override suspend fun getAllExchangeRates(baseCurrency: String): Flow<ExchangeRates> {
        val favoritesCurrencies = database.getFavoritesCurrencies().map { it.code }
        return apiRepository.getExchangeRates(baseCurrency).map {
            val currencies = it.currenciesDTO.map { currencyDTO ->
                currencyDTO.toCurrency(favoritesCurrencies.contains(currencyDTO.code))
            }
            it.toExchangeRates(currencies)
        }
    }

    override suspend fun getFavoritesExchangeRates(baseCurrency: String): Flow<ExchangeRates> {
        var favoritesCurrencies = ""
        database.getFavoritesCurrencies().forEach { currency ->
            favoritesCurrencies += "${currency.code},"
        }
        return apiRepository.getExchangeRates(baseCurrency, favoritesCurrencies).map {
            val currencies = it.currenciesDTO.map { currencyDTO -> currencyDTO.toCurrency(true) }
            it.toExchangeRates(currencies)
        }
    }

    override suspend fun addCurrencyToFavorites(currency: Currency) =
        database.addCurrencyToFavorites(CurrencyDTO(currency.code))

    override suspend fun deleteCurrencyFromFavorites(currency: Currency) =
        database.deleteCurrencyFromFavorites(CurrencyDTO(currency.code))
}