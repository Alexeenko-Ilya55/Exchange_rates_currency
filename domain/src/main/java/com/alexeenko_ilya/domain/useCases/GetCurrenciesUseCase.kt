package com.alexeenko_ilya.domain.useCases

import com.alexeenko_ilya.domain.Repository
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(private val repository: Repository) {
    suspend fun getCurrencies() = repository.getAllCurrencies()
}