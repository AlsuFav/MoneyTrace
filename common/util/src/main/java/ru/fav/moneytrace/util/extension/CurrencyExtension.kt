package ru.fav.moneytrace.util.extension

import java.util.Locale

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

fun Double.formatAmount(locale: Locale = Locale.US): String {
    return String.format(locale, "%.2f", this)
}