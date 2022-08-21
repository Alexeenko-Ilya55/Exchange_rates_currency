package com.alexeenko_ilya.domain.useCases

import com.alexeenko_ilya.domain.Repository
import javax.inject.Inject

class GetExchangeRatesToFavoritesUseCase @Inject constructor(private val repository: Repository) {
    suspend fun getExchangeRatesToFavorites(baseCurrency: String) =
        repository.getFavoritesExchangeRates(baseCurrency)
}