package ru.fav.moneytrace.data.mapper

import ru.fav.moneytrace.domain.model.AccountStateModel
import ru.fav.moneytrace.network.pojo.response.AccountStateResponse
import javax.inject.Inject

class AccountStateMapper @Inject constructor(){
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
}