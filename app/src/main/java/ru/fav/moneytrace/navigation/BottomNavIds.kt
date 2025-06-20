package ru.fav.moneytrace.navigation

sealed class BottomNavIds(val id: String) {
    object Account : BottomNavIds("account")
    object Income : BottomNavIds("income")
    object Settings : BottomNavIds("settings")
    object Expenses : BottomNavIds("expenses")
    object Categories : BottomNavIds("categories")
}