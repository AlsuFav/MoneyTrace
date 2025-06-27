package ru.fav.moneytrace.account.impl.ui.state

sealed class AccountEvent {
    object LoadAccount : AccountEvent()
    object HideErrorDialog : AccountEvent()
}
