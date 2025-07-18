package ru.fav.moneytrace.transaction.impl.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "account_id")
    val accountId: Int,

    @ColumnInfo(name = "account_name")
    val accountName: String,

    @ColumnInfo(name = "account_balance")
    val accountBalance: Double,

    @ColumnInfo(name = "account_currency")
    val accountCurrency: String,

    @ColumnInfo(name = "category_id")
    val categoryId: Int,

    @ColumnInfo(name = "category_name")
    val categoryName: String,

    @ColumnInfo(name = "category_emoji")
    val categoryEmoji: String,

    @ColumnInfo(name = "category_is_income")
    val categoryIsIncome: Boolean,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "transaction_date")
    val transactionDate: Long,

    @ColumnInfo(name = "comment")
    val comment: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long
)