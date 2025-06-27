package ru.fav.moneytrace.expenses.impl.ui.model

data class ExpenseUIModel(
    val id: Int = 0,
    val name: String = "",
    val amount: String = "0.00 ₽",
    val emoji: String = "",
    val comment: String = "",
    val date: String = "01.01.2025",
    val time: String = "00:00"
)