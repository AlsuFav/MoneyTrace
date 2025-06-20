package ru.fav.moneytrace.domain.model

import java.util.Date


data class TransactionModel(
    val id: Int = 0,
    val account: AccountStateModel = AccountStateModel(),
    val category: CategoryModel = CategoryModel(),
    val amount: Double = 0.0,
    val transactionDate: Date = Date(),
    val comment: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
)