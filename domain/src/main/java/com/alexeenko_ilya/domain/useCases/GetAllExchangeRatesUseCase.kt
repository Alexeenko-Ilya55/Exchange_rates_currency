package com.alexeenko_ilya.domain.useCases

import com.alexeenko_ilya.domain.Repository
import javax.inject.Inject

class GetAllExchangeRatesUseCase @Inject constructor(private val repository: Repository) {
    suspend fun getAllExchangeRates(baseCurrency: String) =
        repository.getAllExchangeRates(baseCurrency)
}