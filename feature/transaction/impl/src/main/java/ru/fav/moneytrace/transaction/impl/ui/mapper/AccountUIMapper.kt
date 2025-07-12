package ru.fav.moneytrace.transaction.impl.ui.mapper

import ru.fav.moneytrace.account.api.model.AccountModel
import ru.fav.moneytrace.transaction.impl.ui.model.AccountUIModel
import ru.fav.moneytrace.ui.util.extension.toCurrencySymbol
import javax.inject.Inject

class AccountUIMapper @Inject constructor() {
    fun map(category: AccountModel): AccountUIModel {
        return AccountUIModel(
            id = category.id,
            name = category.name,
            currencySymbol = category.currency.toCurrencySymbol()
        )
    }
}