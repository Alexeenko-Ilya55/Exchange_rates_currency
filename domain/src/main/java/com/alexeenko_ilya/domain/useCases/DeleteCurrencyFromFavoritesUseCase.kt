package com.alexeenko_ilya.domain.useCases

import com.alexeenko_ilya.domain.Repository
import com.alexeenko_ilya.domain.models.Currency
import javax.inject.Inject

class DeleteCurrencyFromFavoritesUseCase @Inject constructor(private val repository: Repository) {
    suspend fun deleteCurrencyFromFavorites(currency: Currency) =
        repository.deleteCurrencyFromFavorites(currency)
}