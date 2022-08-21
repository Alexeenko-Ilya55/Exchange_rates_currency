package com.alexeenko_ilya.domain

import com.alexeenko_ilya.domain.models.Currency
import com.alexeenko_ilya.domain.models.ExchangeRates
import com.alexeenko_ilya.domain.useCases.GetAllExchangeRatesUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class GetAllExchangeRatesUseCaseTest {

    private val repository = mock<Repository>()
    private val getAllExchangeRatesUseCase = GetAllExchangeRatesUseCase(repository)

    @Test
    fun `should get all exchange rates from the repository`(): Unit = runBlocking {
        val expected = flowOf(ExchangeRates("USD", listOf(Currency("EUR", false)), emptyList()))

        Mockito.`when`(repository.getAllExchangeRates("USD")).thenReturn(expected)

        val actual = getAllExchangeRatesUseCase.getAllExchangeRates("USD")

        actual.collect {
            Assertions.assertEquals(it.currencies.first().code, "EUR")
        }

        verify(repository, times(1)).getAllExchangeRates("USD")
    }
}