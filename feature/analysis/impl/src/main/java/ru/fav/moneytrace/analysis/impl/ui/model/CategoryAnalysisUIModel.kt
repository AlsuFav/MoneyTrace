package ru.fav.moneytrace.analysis.impl.ui.model

data class CategoryAnalysisUIModel(
    val id: Int = 0,
    val name: String = "",
    val amount: String = "0.00 ₽",
    val emoji: String = "",
    val percentage: String = "",
)