package ru.fav.moneytrace.account.impl.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "account_category_details",
    primaryKeys = ["account_id", "category_id"],
    foreignKeys = [
        ForeignKey(
            entity = AccountDetailsEntity::class,
            parentColumns = ["id"],
            childColumns = ["account_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AccountCategoryDetailsEntity(
    @ColumnInfo(name = "account_id")
    val accountId: Int,

    @ColumnInfo(name = "category_id")
    val categoryId: Int,

    @ColumnInfo(name = "category_name")
    val categoryName: String,

    @ColumnInfo(name = "emoji")
    val emoji: String,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "is_income")
    val isIncome: Boolean
)