package ru.fav.moneytrace.domain.model

data class CategoryModel (
    val id: Int = 0,
    val name: String = "",
    val emoji: String = "",
    val isIncome: Boolean = false,
)