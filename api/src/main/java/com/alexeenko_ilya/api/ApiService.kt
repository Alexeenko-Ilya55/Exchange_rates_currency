package com.alexeenko_ilya.api

import com.alexeenko_ilya.api.models.ResponseCurrency
import com.alexeenko_ilya.api.models.ResponseExchangeRates
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val END_POINT = "EndPoint"
const val API_KEY = "api_key"
const val CURRENCY_INFO = "currencies"
const val FROM = "from"
const val TO_CURRENCIES = "to"

interface ApiService {

    @GET(CURRENCY_INFO)
    suspend fun getCurrenciesInfo(
        @Query(API_KEY) apiKey: String
    ): ResponseCurrency

    @GET("{$END_POINT}")
    suspend fun getExchangeRates(
        @Path(END_POINT) endPoint: String,
        @Query(FROM) from: String,
        @Query(TO_CURRENCIES) favoriteCurrencies: String?,
        @Query(API_KEY) apiKey: String
    ): ResponseExchangeRates
}
