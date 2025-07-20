package ru.fav.moneytrace.transaction.impl.data.mapper

import ru.fav.moneytrace.transaction.api.model.AccountStateModel
import ru.fav.moneytrace.transaction.impl.data.local.entity.AccountStateEntity
import ru.fav.moneytrace.transaction.impl.data.remote.pojo.response.AccountStateResponse
import javax.inject.Inject

class AccountStateMapper @Inject constructor() {

    fun map(input: AccountStateResponse?): AccountStateModel {
        return input?.let {
            AccountStateModel(
                id = it.id ?: 0,
                name = it.name.orEmpty(),
                balance = input.balance?.toDoubleOrNull() ?: 0.0,
                currency = it.currency.orEmpty(),
            )
        } ?: AccountStateModel()
    }

    fun mapEntityToDomain(input: AccountStateEntity): AccountStateModel {
        return AccountStateModel(
            id = input.id,
            name = input.name,
            balance = input.balance,
            currency = input.currency
        )
    }

    fun mapDomainToEntity(model: AccountStateModel): AccountStateEntity {
        return AccountStateEntity(
            id = model.id,
            name = model.name,
            balance = model.balance,
            currency = model.currency
        )
    }
}