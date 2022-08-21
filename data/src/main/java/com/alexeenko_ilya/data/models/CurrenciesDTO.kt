package com.alexeenko_ilya.data.models

import com.alexeenko_ilya.domain.models.Currency

data class CurrencyDTO(
    val code: String,
) {
    fun toCurrency(isFavorite: Boolean) = Currency(code, isFavorite)
}