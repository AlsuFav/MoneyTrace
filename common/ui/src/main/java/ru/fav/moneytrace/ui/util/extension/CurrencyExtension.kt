package ru.fav.moneytrace.ui.util.extension

import ru.fav.moneytrace.ui.R
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

fun String.toCurrencyNameResId(): Int {
    return when (this) {
        "RUB" -> R.string.currency_rub
        "USD" -> R.string.currency_usd
        "EUR" -> R.string.currency_eur
        "GBP" -> R.string.currency_gbp
        "JPY" -> R.string.currency_jpy
        else -> R.string.currency_unknown
    }
}

fun Double.formatAmount(locale: Locale = Locale.US): String {
    return String.format(locale, "%.2f", this)
}