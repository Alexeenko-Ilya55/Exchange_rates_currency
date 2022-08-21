package com.alexeenko_ilya.domain.models

data class ExchangeRates(
    val base: String,
    val currencies: List<Currency>,
    var values: List<Double>
)