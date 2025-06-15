package ru.fav.moneytrace.domain.model

data class Account(
    val id: Int = 0,
    val name: String = "",
    val balance: String = "",
    val currency: String = ""
)