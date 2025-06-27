package ru.fav.moneytrace.income.impl.ui.model

data class IncomeUIModel(
    val id: Int = 0,
    val name: String = "",
    val amount: String = "0.00 ₽",
    val comment: String = "",
    val date: String = "01.01.2025",
    val time: String = "00:00"
)