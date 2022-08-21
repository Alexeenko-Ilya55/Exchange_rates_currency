package com.alexeenko_ilya.domain.useCases

import com.alexeenko_ilya.domain.Repository
import com.alexeenko_ilya.domain.models.Currency
import javax.inject.Inject

class AddCurrencyToFavoritesUseCase @Inject constructor(private val repository: Repository) {
    suspend fun addCurrencyToFavorites(currency: Currency) =
        repository.addCurrencyToFavorites(currency)
}