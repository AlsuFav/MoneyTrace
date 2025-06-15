package ru.fav.moneytrace.domain.model

data class Transaction(
    val id: Int = 0,
    val category: String = "",
    val emoji: String = "",
    val isIncome: Boolean = false,
    val comment: String = "",
    val amount: String = "",
    val currency:  String = ""
)