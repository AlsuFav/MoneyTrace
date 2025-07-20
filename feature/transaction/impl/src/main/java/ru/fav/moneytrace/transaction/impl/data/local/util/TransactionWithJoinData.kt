package ru.fav.moneytrace.transaction.impl.data.local.util

import androidx.room.ColumnInfo

data class TransactionWithJoinData(
    val id: Int,
    @ColumnInfo(name = "account_id") val accountId: Int,
    @ColumnInfo(name = "category_id") val categoryId: Int,
    val amount: Double,
    @ColumnInfo(name = "transaction_date") val transactionDate: String,
    val comment: String,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "updated_at") val updatedAt: String,

    @ColumnInfo(name = "account_name") val accountName: String,
    @ColumnInfo(name = "account_balance") val accountBalance: Double,
    @ColumnInfo(name = "account_currency") val accountCurrency: String,

    @ColumnInfo(name = "category_name") val categoryName: String,
    @ColumnInfo(name = "category_emoji") val categoryEmoji: String,
    @ColumnInfo(name = "category_is_income") val categoryIsIncome: Boolean
)