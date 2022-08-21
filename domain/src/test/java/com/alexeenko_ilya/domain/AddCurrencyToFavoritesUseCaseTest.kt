package com.alexeenko_ilya.domain

import com.alexeenko_ilya.domain.models.Currency
import com.alexeenko_ilya.domain.useCases.AddCurrencyToFavoritesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class AddCurrencyToFavoritesUseCaseTest {

    private val repository = mock<Repository>()
    private val addCurrencyToFavoritesUseCase = AddCurrencyToFavoritesUseCase(repository)

    @Test
    fun `should get all exchange rates from the repository`(): Unit = runBlocking {
        val currency = Currency("EUR", false)
        addCurrencyToFavoritesUseCase.addCurrencyToFavorites(currency)
        verify(repository, times(1)).addCurrencyToFavorites(currency)
    }
}