package ru.fav.moneytrace.domain.model

data class Stat (
    val categoryId: Int = 0,
    val categoryName: String = "",
    val emoji: String = "",
    val amount: String = "",
)