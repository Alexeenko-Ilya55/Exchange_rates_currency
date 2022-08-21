package com.alexeenko_ilya.api.models

import com.alexeenko_ilya.data.models.CurrencyDTO
import com.alexeenko_ilya.data.models.ExchangeRatesDTO

data class ResponseExchangeRates(
    var base: String,
    var ms: Int,
    var results: Map<String, Double>,
    var updated: String
) {
    fun toExchangeRatesDTO(): ExchangeRatesDTO {
        val listCurrencyDTO = mutableListOf<CurrencyDTO>()
        results.keys.forEach { listCurrencyDTO.add(CurrencyDTO(it, false)) }
        return ExchangeRatesDTO(base, listCurrencyDTO, results.values.toList())
    }
}