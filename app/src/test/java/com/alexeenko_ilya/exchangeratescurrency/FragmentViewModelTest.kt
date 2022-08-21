package com.alexeenko_ilya.exchangeratescurrency

import com.alexeenko_ilya.domain.models.Currency
import com.alexeenko_ilya.domain.useCases.*
import com.alexeenko_ilya.exchangeratescurrency.viewModels.FragmentViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify


class FragmentViewModelTest {
    private val addCurrencyToFavoritesUseCase = mock<AddCurrencyToFavoritesUseCase>()
    private val deleteCurrencyFromFavoritesUseCase = mock<DeleteCurrencyFromFavoritesUseCase>()
    private val getAllExchangeRatesUseCase = mock<GetAllExchangeRatesUseCase>()
    private val getCurrenciesUseCase = mock<GetCurrenciesUseCase>()
    private val getExchangeRatesToFavoritesUseCase = mock<GetExchangeRatesToFavoritesUseCase>()

    private val fragmentViewModel = FragmentViewModel(
        addCurrencyToFavoritesUseCase,
        deleteCurrencyFromFavoritesUseCase,
        getCurrenciesUseCase,
        getAllExchangeRatesUseCase,
        getExchangeRatesToFavoritesUseCase
    )

    @Test
    fun `should add currency to favorites`(): Unit = runBlocking {
        val currency = Currency("EUR", false)
        fragmentViewModel.addCurrencyToFavorites(currency)
        verify(addCurrencyToFavoritesUseCase, times(1)).addCurrencyToFavorites(currency)
    }

    @Test
    fun `should delete currency from favorites`(): Unit = runBlocking {
        val currency = Currency("EUR", false)
        fragmentViewModel.deleteCurrencyFromFavorites(currency)
        verify(deleteCurrencyFromFavoritesUseCase, times(1)).deleteCurrencyFromFavorites(currency)
    }

    @Test
    fun `should get all exchange rates`(): Unit = runBlocking {
        fragmentViewModel.getAllExchangeRates("USD")
        verify(getAllExchangeRatesUseCase, times(1)).getAllExchangeRates("USD")
    }

    @Test
    fun `should get favorites exchange rates`(): Unit = runBlocking {
        fragmentViewModel.getExchangeRatesToFavoritesUseCase("USD")
        verify(getExchangeRatesToFavoritesUseCase, times(1)).getExchangeRatesToFavorites("USD")
    }

    @Test
    fun `should get currencies`(): Unit = runBlocking {
        fragmentViewModel.getCurrencies()
        verify(getCurrenciesUseCase, times(1)).getCurrencies()
    }

}