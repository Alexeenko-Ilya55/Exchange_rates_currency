package com.alexeenko_ilya.api

import com.alexeenko_ilya.api.models.ResponseCurrency
import com.alexeenko_ilya.api.models.ResponseExchangeRates
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito.*
import org.mockito.kotlin.mock


class ApiRepositoryUnitTest {
    private val apiService = mock<ApiService>()
    private val apiRepositoryImpl = ApiRepositoryImpl(apiService)

    @AfterEach
    fun tearDown() {
        reset(apiService)
    }

    @Test
    fun `should get currencies from api`(): Unit = runBlocking {

        val expected = ResponseCurrency(mapOf("USD" to "Dollar"),1)

        `when`(
            apiService.getCurrenciesInfo(BuildConfig.API_KEY)
        ).thenReturn(expected)

        val actual = apiRepositoryImpl.getCurrencies()

        actual.collectLatest {
            Assertions.assertEquals(expected.currencies.keys.first(), it.first().code)
        }

        verify(apiService, times(1)).getCurrenciesInfo(BuildConfig.API_KEY)
    }

    @Test
    fun `should get all exchange rates from api`(): Unit = runBlocking {
        val expected = ResponseExchangeRates("USD",0 ,mapOf("USD" to 2.44),"date")

        `when`(
            apiService.getExchangeRates(ALL_CURRENCIES, "USD", null, BuildConfig.API_KEY)
        ).thenReturn(expected)

        val actual = apiRepositoryImpl.getExchangeRates("USD")

        actual.collectLatest {
            Assertions.assertEquals(expected.results.keys.first(), it.currenciesDTO.first().code)
        }

        verify(apiService, times(1)).getExchangeRates(
            ALL_CURRENCIES,
            "USD",
            null,
            BuildConfig.API_KEY
        )
    }

    @Test
    fun `should get favorites exchange rates from api`(): Unit = runBlocking {

        val expected = ResponseExchangeRates("USD",0 ,mapOf("USD" to 2.44),"date")

        `when`(
            apiService.getExchangeRates(FAVORITES_CURRENCIES, "USD", "EUR,ALL", BuildConfig.API_KEY)
        ).thenReturn(expected)

        val actual = apiRepositoryImpl.getExchangeRates("USD","EUR,ALL")

        actual.collectLatest {
            Assertions.assertEquals(expected.results.keys.first(), it.currenciesDTO.first().code)
        }

        verify(apiService, times(1)).getExchangeRates(
            FAVORITES_CURRENCIES,
            "USD",
            "EUR,ALL",
            BuildConfig.API_KEY
        )
    }
}