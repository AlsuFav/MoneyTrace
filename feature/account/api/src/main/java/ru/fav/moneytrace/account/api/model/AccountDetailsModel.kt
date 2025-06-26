package ru.fav.moneytrace.account.api.model

import java.util.Date

data class AccountDetailsModel (
    val id: Int = 0,
    val name: String = "",
    val balance: Double = 0.0,
    val currency: String = "",
    val incomeCategories: List<CategoryDetailsModel> = emptyList(),
    val expenseCategories: List<CategoryDetailsModel> = emptyList(),
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
)