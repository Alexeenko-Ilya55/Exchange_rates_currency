package com.alexeenko_ilya.api.models

import com.alexeenko_ilya.data.models.CurrencyDTO

data class ResponseCurrency(
    var currencies: Map<String, String>,
    var ms: Int
) {
    fun toCurrencyDTO(): List<CurrencyDTO> = currencies.keys.map { CurrencyDTO(it) }
}