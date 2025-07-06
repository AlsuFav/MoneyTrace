package ru.fav.moneytrace.account.impl.ui.update.state

import androidx.compose.runtime.Immutable
import ru.fav.moneytrace.account.impl.ui.model.AccountUIModel
import ru.fav.moneytrace.account.impl.ui.model.CurrencyUIModel
import java.util.Currency

@Immutable
data class AccountUpdateState(
    val isLoading: Boolean = false,
    val account: AccountUIModel = AccountUIModel(),
    val currencies: List<CurrencyUIModel> = emptyList(),
    val showErrorDialog: String? = null,
    val showBottomSheet: Boolean = false
)
