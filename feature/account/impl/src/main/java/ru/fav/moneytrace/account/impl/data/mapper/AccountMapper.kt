package ru.fav.moneytrace.account.impl.data.mapper

import ru.fav.moneytrace.account.api.model.AccountModel
import ru.fav.moneytrace.account.impl.data.local.entity.AccountEntity
import ru.fav.moneytrace.account.impl.data.remote.pojo.response.AccountResponse
import ru.fav.moneytrace.util.DateHelper
import java.util.Date
import javax.inject.Inject
import kotlin.collections.map
import kotlin.let
import kotlin.text.orEmpty

class AccountMapper @Inject constructor() {

    fun mapNetworkToDomain(input: AccountResponse?): AccountModel {
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

    fun mapEntityToDomain(input: AccountEntity): AccountModel {
        return AccountModel(
            id = input.id,
            name = input.name,
            balance = input.balance,
            currency = input.currency,
            createdAt = Date(input.createdAt),
            updatedAt = Date(input.updatedAt)
        )
    }

    fun mapDomainToEntity(model: AccountModel): AccountEntity {
        return AccountEntity(
            id = model.id,
            name = model.name,
            balance = model.balance,
            currency = model.currency,
            createdAt = model.createdAt.time,
            updatedAt = model.updatedAt.time
        )
    }

    fun mapNetworkToDomainList(input: List<AccountResponse>?): List<AccountModel> {
        return input?.map { mapNetworkToDomain(it) } ?: emptyList()
    }

    fun mapEntityToDomainList(input: List<AccountEntity>): List<AccountModel> {
        return input.map { mapEntityToDomain(it) }
    }

    fun mapDomainToEntityList(models: List<AccountModel>): List<AccountEntity> {
        return models.map { mapDomainToEntity(it) }
    }
}