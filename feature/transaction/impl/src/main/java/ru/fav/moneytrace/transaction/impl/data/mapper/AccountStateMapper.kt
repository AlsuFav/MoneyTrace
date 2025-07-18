package ru.fav.moneytrace.transaction.impl.data.mapper

import ru.fav.moneytrace.transaction.api.model.AccountStateModel
import ru.fav.moneytrace.transaction.impl.data.remote.pojo.response.AccountStateResponse
import javax.inject.Inject

class AccountStateMapper @Inject constructor() {

    fun mapNetworkToDomain(input: AccountStateResponse?): AccountStateModel {
        return input?.let {
            AccountStateModel(
                id = it.id ?: 0,
                name = it.name.orEmpty(),
                balance = input.balance?.toDoubleOrNull() ?: 0.0,
                currency = it.currency.orEmpty(),
            )
        } ?: AccountStateModel()
    }

    fun mapEntityToDomain(
        accountId: Int,
        accountName: String,
        accountBalance: Double,
        accountCurrency: String
    ): AccountStateModel {
        return AccountStateModel(
            id = accountId,
            name = accountName,
            balance = accountBalance,
            currency = accountCurrency
        )
    }
}