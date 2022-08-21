package com.alexeenko_ilya.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexeenko_ilya.data.models.CurrencyDTO

const val TABLE_NAME = "favoritesCurrencies"

@Entity(tableName = TABLE_NAME)
data class CurrencyEntity(
    @PrimaryKey
    val code: String
) {
    fun toCurrencyDTO() = CurrencyDTO(code)
}
