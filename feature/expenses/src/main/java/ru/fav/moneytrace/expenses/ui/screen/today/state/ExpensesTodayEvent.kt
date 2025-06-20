package ru.fav.moneytrace.expenses.ui.screen.today.state

sealed class ExpensesTodayEvent {
    object LoadExpenses : ExpensesTodayEvent()
    object HideErrorDialog : ExpensesTodayEvent()
}