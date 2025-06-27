package ru.fav.moneytrace.account.impl.ui.state

import androidx.compose.runtime.Immutable
import ru.fav.moneytrace.account.impl.ui.model.AccountUIModel

@Immutable
data class AccountState(
    val isLoading: Boolean = false,
    val account: AccountUIModel = AccountUIModel(),
    val showErrorDialog: String? = null

)
