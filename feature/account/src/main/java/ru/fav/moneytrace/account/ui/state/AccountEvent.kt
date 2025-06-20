package ru.fav.moneytrace.account.ui.state

sealed class AccountEvent {
    object LoadAccount : AccountEvent()
    object HideErrorDialog : AccountEvent()
}
