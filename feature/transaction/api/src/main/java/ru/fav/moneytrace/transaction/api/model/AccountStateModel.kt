package ru.fav.moneytrace.transaction.api.model

data class AccountStateModel(
    val id: Int = 0,
    val name: String = "",
    val balance: Double = 0.0,
    val currency: String = "",
)