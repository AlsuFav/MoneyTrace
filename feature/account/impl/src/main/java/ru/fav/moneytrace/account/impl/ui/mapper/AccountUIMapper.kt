package ru.fav.moneytrace.account.impl.ui.mapper

import ru.fav.moneytrace.account.api.model.AccountModel
import ru.fav.moneytrace.account.impl.ui.model.AccountUIModel
import ru.fav.moneytrace.ui.util.extension.formatAmount
import ru.fav.moneytrace.ui.util.extension.toCurrencySymbol
import javax.inject.Inject

class AccountUIMapper @Inject constructor() {
    fun map(account: AccountModel): AccountUIModel {
        return AccountUIModel(
            id = account.id,
            name = account.name,
            balance = "${account.balance.formatAmount()} ${account.currency.toCurrencySymbol()}",
            currencySymbol = account.currency.toCurrencySymbol()
        )
    }
}