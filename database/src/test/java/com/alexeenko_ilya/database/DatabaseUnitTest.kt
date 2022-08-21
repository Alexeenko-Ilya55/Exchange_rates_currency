package com.alexeenko_ilya.database

import com.alexeenko_ilya.data.models.CurrencyDTO
import com.alexeenko_ilya.database.models.CurrencyEntity
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions

import org.mockito.Mockito.*
import org.mockito.kotlin.mock

class DatabaseUnitTest {
    private val dao = mock<Dao>()
    private val databaseImpl = DatabaseImpl(dao)

    @AfterEach
    fun tearDown() {
        reset(dao)
    }

    @Test
    fun `should get currencies from database`(): Unit = runBlocking {
        val expected = emptyList<CurrencyEntity>()

        `when`(
            dao.getFavoritesCurrencies()
        ).thenReturn(expected)

        val actual = databaseImpl.getFavoritesCurrencies()

        Assertions.assertEquals(expected, actual)
        verify(dao, times(1)).getFavoritesCurrencies()
    }

    @Test
    fun `should delete currency from database`(): Unit = runBlocking {
        val currencyDTO = CurrencyDTO("USD",true)
        databaseImpl.deleteCurrencyFromFavorites(currencyDTO)
        verify(dao, times(1)).deleteFromFavorites(CurrencyEntity("USD"))
    }

    @Test
    fun `should add currency to database`(): Unit = runBlocking {
        val currencyDTO = CurrencyDTO("USD",true)
        databaseImpl.addCurrencyToFavorites(currencyDTO)
        verify(dao, times(1)).addToFavorites(CurrencyEntity("USD"))
    }
}