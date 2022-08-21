package com.alexeenko_ilya.data

import com.alexeenko_ilya.data.models.CurrencyDTO
import com.alexeenko_ilya.data.models.ExchangeRatesDTO
import com.alexeenko_ilya.domain.models.Currency
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock


class RepositoryUnitTest {
    private val apiRepository = mock<ApiRepository>()
    private val database = mock<Database>()
    private val repositoryImpl = RepositoryImpl(apiRepository, database)

    @AfterEach
    fun tearDown() {
        Mockito.reset(apiRepository, database, repositoryImpl)
    }

    @Test
    fun `should get currencies from repository`(): Unit = runBlocking {
        val expected = flowOf(listOf(CurrencyDTO("USD", true)))

        Mockito.`when`(
            apiRepository.getCurrencies()
        ).thenReturn(expected)

        val actual = repositoryImpl.getAllCurrencies()

        actual.collectLatest {
            Assertions.assertEquals(it.first().code, "USD")
        }
        Mockito.verify(apiRepository, Mockito.times(1)).getCurrencies()
    }

    @Test
    fun `should get all exchange rates from repository`(): Unit = runBlocking {
        val expected = flowOf(
            ExchangeRatesDTO(
                "USD",
                listOf(CurrencyDTO("EUR", false), CurrencyDTO("ALL", false)),
                listOf(3.22, 3.12)
            )
        )
        val listCurrencyDTO = listOf(CurrencyDTO("EUR", true))

        Mockito.`when`(
            apiRepository.getExchangeRates("USD")
        ).thenReturn(expected)
        Mockito.`when`(
            database.getFavoritesCurrencies()
        ).thenReturn(listCurrencyDTO)

        val actual = repositoryImpl.getAllExchangeRates("USD")

        actual.collectLatest {
            Assertions.assertEquals(it.currencies.first().code, "EUR")
            Assertions.assertEquals(it.currencies.first().isFavorite, true)
            Assertions.assertEquals(it.currencies[1].isFavorite, false)
        }
        Mockito.verify(apiRepository, Mockito.times(1)).getExchangeRates("USD")
    }

    @Test
    fun `should get favorites exchange rates from repository`(): Unit = runBlocking {
        val expected =
            flowOf(ExchangeRatesDTO("USD", listOf(CurrencyDTO("EUR", false)), listOf(3.22)))
        val listCurrencyDTO = listOf(CurrencyDTO("EUR", true))


        Mockito.`when`(
            apiRepository.getExchangeRates("USD", "EUR,")
        ).thenReturn(expected)
        Mockito.`when`(
            database.getFavoritesCurrencies()
        ).thenReturn(listCurrencyDTO)

        val actual = repositoryImpl.getFavoritesExchangeRates("USD")

        actual.collectLatest {
            Assertions.assertEquals(it.currencies.first().code, "EUR")
            Assertions.assertEquals(it.currencies.first().isFavorite, true)
        }
        Mockito.verify(apiRepository, Mockito.times(1)).getExchangeRates("USD", "EUR,")
    }


    @Test
    fun `should add currency in database`(): Unit = runBlocking {
        val currencyDTO = CurrencyDTO("USD",true)
        val currency = Currency("USD",true)
        repositoryImpl.addCurrencyToFavorites(currency)
        Mockito.verify(database, Mockito.times(1)).addCurrencyToFavorites(currencyDTO)
    }

    @Test
    fun `should delete currency in database`(): Unit = runBlocking {
        val currencyDTO = CurrencyDTO("USD",true)
        val currency = Currency("USD",true)
        repositoryImpl.deleteCurrencyFromFavorites(currency)
        Mockito.verify(database, Mockito.times(1)).deleteCurrencyFromFavorites(currencyDTO)
    }

}