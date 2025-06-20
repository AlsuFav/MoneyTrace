package ru.fav.moneytrace.income.ui.screen.today.state

sealed class IncomeTodayEvent {
    object LoadIncome : IncomeTodayEvent()
    object HideErrorDialog : IncomeTodayEvent()
}