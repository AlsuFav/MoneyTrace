package ru.fav.moneytrace.account.impl.data.mapper

import ru.fav.moneytrace.account.api.model.AccountModel
import ru.fav.moneytrace.account.impl.data.remote.pojo.response.AccountResponse
import ru.fav.moneytrace.util.DateHelper
import java.util.Date
import javax.inject.Inject
import kotlin.collections.map
import kotlin.let
import kotlin.text.orEmpty

class AccountMapper @Inject constructor(){
    fun map(input: AccountResponse?): AccountModel {
        return input?.let {
            AccountModel(
                id = it.id ?: 0,
                name = it.name.orEmpty(),
                balance = input.balance?.toDoubleOrNull() ?: 0.0,
                currency = it.currency.orEmpty(),
                createdAt = input.createdAt?.let { DateHelper.parseApiDateTime(it) } ?: Date(),
                updatedAt = input.updatedAt?.let { DateHelper.parseApiDateTime(it) } ?: Date(),
                )
        } ?: AccountModel()
    }

    fun mapList(input: List<AccountResponse>?): List<AccountModel> {
        return input?.map { map(it) } ?: emptyList()
    }
}