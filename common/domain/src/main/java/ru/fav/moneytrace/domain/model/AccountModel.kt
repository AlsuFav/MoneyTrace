package ru.fav.moneytrace.domain.model

import java.util.Date

data class AccountModel(
    val id: Int = 0,
    val name: String = "",
    val balance: Double = 0.0,
    val currency: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
)