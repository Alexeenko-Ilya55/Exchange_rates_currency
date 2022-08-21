package com.alexeenko_ilya.api

import com.alexeenko_ilya.data.ApiRepository
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

const val ALL_CURRENCIES = "fetch-all"
const val FAVORITES_CURRENCIES = "fetch-multi"

class ApiRepositoryImpl @Inject constructor(private val apiService: ApiService) : ApiRepository {

    override suspend fun getCurrencies() =
        flowOf(apiService.getCurrenciesInfo(BuildConfig.API_KEY).toCurrencyDTO())

    override suspend fun getExchangeRates(baseCurrency: String) = flowOf(
        apiService.getExchangeRates(
            ALL_CURRENCIES, baseCurrency, null, BuildConfig.API_KEY
        ).toExchangeRatesDTO()
    )

    override suspend fun getExchangeRates(baseCurrency: String, toCurrencies: String) = flowOf(
        apiService.getExchangeRates(
            FAVORITES_CURRENCIES, baseCurrency, toCurrencies, BuildConfig.API_KEY
        ).toExchangeRatesDTO()
    )
}