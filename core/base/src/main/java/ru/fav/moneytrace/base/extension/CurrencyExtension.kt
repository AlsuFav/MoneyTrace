package ru.fav.moneytrace.base.extension

fun String.toCurrencySymbol(): String {
    return when (this) {
        "RUB" -> "₽"
        "USD" -> "$"
        "EUR" -> "€"
        "GBP" -> "£"
        "JPY" -> "¥"
        else -> this
    }
}