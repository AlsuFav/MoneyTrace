package ru.fav.moneytrace.expenses.ui.screen.today.state

sealed class ExpensesTodayEvent {
    object Retry : ExpensesTodayEvent()
    object HideErrorDialog : ExpensesTodayEvent()
}