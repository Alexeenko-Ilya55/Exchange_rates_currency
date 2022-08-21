package com.alexeenko_ilya.domain

import com.alexeenko_ilya.domain.models.Currency
import com.alexeenko_ilya.domain.useCases.GetCurrenciesUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify


class GetCurrenciesUseCaseTest {
    private val repository = mock<Repository>()
    private val getCurrenciesUseCase = GetCurrenciesUseCase(repository)

    @Test
    fun `should get currencies from the repository`(): Unit = runBlocking {
        val expected = flowOf(listOf(Currency("USD", false)))

        Mockito.`when`(repository.getAllCurrencies()).thenReturn(expected)

        val actual = getCurrenciesUseCase.getCurrencies()

        actual.collect {
            Assertions.assertEquals(it.first().code, "USD")
        }

        verify(repository, times(1)).getAllCurrencies()
    }
}