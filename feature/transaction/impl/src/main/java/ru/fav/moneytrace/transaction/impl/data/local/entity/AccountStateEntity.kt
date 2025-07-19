package ru.fav.moneytrace.transaction.impl.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account_states")
data class AccountStateEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "balance")
    val balance: Double,

    @ColumnInfo(name = "currency")
    val currency: String
)