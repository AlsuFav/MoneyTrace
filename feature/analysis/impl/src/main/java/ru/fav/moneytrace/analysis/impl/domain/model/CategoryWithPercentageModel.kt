package ru.fav.moneytrace.analysis.impl.domain.model

data class CategoryWithPercentageModel(
    val id: Int,
    val name: String,
    val emoji: String,
    val amount: Double,
    val currency: String,
    val percentage: Double,
)