package ru.fav.moneytrace.account.ui.state

sealed class AccountEvent {
    object Retry : AccountEvent()
    object HideErrorDialog : AccountEvent()
}
