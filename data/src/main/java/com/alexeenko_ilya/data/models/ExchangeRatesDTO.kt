package com.alexeenko_ilya.data.models

import com.alexeenko_ilya.domain.models.Currency
import com.alexeenko_ilya.domain.models.ExchangeRates

data class ExchangeRatesDTO(
    var base: String,
    val currenciesDTO: List<CurrencyDTO>,
    var values: List<Double>
) {
    fun toExchangeRates(currencies: List<Currency>) = ExchangeRates(base, currencies, values)
}