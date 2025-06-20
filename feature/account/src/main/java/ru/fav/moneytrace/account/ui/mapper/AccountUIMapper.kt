package ru.fav.moneytrace.account.ui.mapper

import ru.fav.moneytrace.account.ui.model.AccountUIModel
import ru.fav.moneytrace.domain.model.AccountModel
import ru.fav.moneytrace.util.extension.formatAmount
import ru.fav.moneytrace.util.extension.toCurrencySymbol
import javax.inject.Inject

class AccountUIMapper @Inject constructor() {
    fun map(account: AccountModel): AccountUIModel {
        return AccountUIModel(
            id = account.id,
            name = account.name,
            balance = "${account.balance.formatAmount()} ${account.currency.toCurrencySymbol()}",
            currency = account.currency.toCurrencySymbol()
        )
    }
}