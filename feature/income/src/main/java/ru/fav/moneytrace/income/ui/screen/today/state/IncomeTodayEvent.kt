package ru.fav.moneytrace.income.ui.screen.today.state

sealed class IncomeTodayEvent {
    object Retry : IncomeTodayEvent()
    object HideErrorDialog : IncomeTodayEvent()
}