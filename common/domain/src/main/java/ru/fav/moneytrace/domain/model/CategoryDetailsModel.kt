package ru.fav.moneytrace.domain.model

data class CategoryDetailsModel (
    val id: Int = 0,
    val name: String = "",
    val emoji: String = "",
    val amount: Double = 0.0
)