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
) :
    Repository {

    override suspend fun getAllCurrencies(): Flow<List<Currency>> =
        apiRepository.getCurrencies().map {
            it.map { currencyDTO -> currencyDTO.toCurrency() }
        }

    override suspend fun getAllExchangeRates(baseCurrency: String): Flow<ExchangeRates> {
        val favoritesCurrency = database.getFavoritesCurrencies().map { it.code }
        val exchangeRates = apiRepository.getExchangeRates(baseCurrency).map {
            it.currenciesDTO.forEach { currencyDTO ->
                if (favoritesCurrency.contains(currencyDTO.code))
                    currencyDTO.isFavorite = true
            }
            it.toExchangeRates()
        }
        return exchangeRates
    }

    override suspend fun getFavoritesExchangeRates(baseCurrency: String): Flow<ExchangeRates> {
        var favoritesCurrencies = ""
        database.getFavoritesCurrencies().forEach { currency ->
            favoritesCurrencies += "${currency.code},"
        }
        return apiRepository.getExchangeRates(baseCurrency, favoritesCurrencies).map {
            it.currenciesDTO.forEach { currencyDTO -> currencyDTO.isFavorite = true }
            it.toExchangeRates()
        }
    }

    override suspend fun addCurrencyToFavorites(currency: Currency) =
        database.addCurrencyToFavorites(CurrencyDTO(currency.code, currency.isFavorite))

    override suspend fun deleteCurrencyFromFavorites(currency: Currency) =
        database.deleteCurrencyFromFavorites(CurrencyDTO(currency.code, currency.isFavorite))
}