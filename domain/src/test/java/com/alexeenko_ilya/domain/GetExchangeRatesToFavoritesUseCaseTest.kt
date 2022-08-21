package com.alexeenko_ilya.domain

import com.alexeenko_ilya.domain.models.Currency
import com.alexeenko_ilya.domain.models.ExchangeRates
import com.alexeenko_ilya.domain.useCases.GetExchangeRatesToFavoritesUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class GetExchangeRatesToFavoritesUseCaseTest {

    private val repository = mock<Repository>()
    private val getExchangeRatesToFavoritesUseCase = GetExchangeRatesToFavoritesUseCase(repository)

    @Test
    fun `should get favorites exchange rates from the repository`(): Unit = runBlocking {
        val expected = flowOf(ExchangeRates("USD", listOf(Currency("EUR", false)), emptyList()))

        Mockito.`when`(repository.getFavoritesExchangeRates("USD")).thenReturn(expected)

        val actual = getExchangeRatesToFavoritesUseCase.getExchangeRatesToFavorites("USD")

        actual.collect {
            Assertions.assertEquals(it.currencies.first().code, "EUR")
        }

        verify(repository, times(1)).getFavoritesExchangeRates("USD")
    }
}