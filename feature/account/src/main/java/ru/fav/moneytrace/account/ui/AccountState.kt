package ru.fav.moneytrace.account.ui

import ru.fav.moneytrace.domain.model.Account

data class AccountState(
    val account: Account = Account()
)
