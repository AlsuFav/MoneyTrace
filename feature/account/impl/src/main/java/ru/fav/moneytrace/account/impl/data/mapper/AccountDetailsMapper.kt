package ru.fav.moneytrace.account.impl.data.mapper

import ru.fav.moneytrace.account.api.model.AccountDetailsModel
import ru.fav.moneytrace.account.api.model.CategoryDetailsModel
import ru.fav.moneytrace.account.impl.data.local.entity.AccountCategoryDetailsEntity
import ru.fav.moneytrace.account.impl.data.local.entity.AccountDetailsEntity
import ru.fav.moneytrace.account.impl.data.remote.pojo.response.AccountDetailsResponse
import ru.fav.moneytrace.util.DateHelper
import java.util.Date
import javax.inject.Inject

class AccountDetailsMapper @Inject constructor(
    private val categoryDetailsMapper: CategoryDetailsMapper
) {

    fun mapNetworkToDomain(input: AccountDetailsResponse?): AccountDetailsModel {
        return input?.let {
            AccountDetailsModel(
                id = input.id ?: 0,
                name = input.name.orEmpty(),
                balance = input.balance?.toDoubleOrNull() ?: 0.0,
                currency = input.currency.orEmpty(),
                incomeCategories = categoryDetailsMapper.mapList(input.incomeStats),
                expenseCategories = categoryDetailsMapper.mapList(input.expenseStats),
                createdAt = input.createdAt?.let { DateHelper.parseApiDateTime(it) } ?: Date(),
                updatedAt = input.updatedAt?.let { DateHelper.parseApiDateTime(it) } ?: Date(),
            )
        } ?: AccountDetailsModel()
    }

    fun mapEntityToDomain(
        accountDetails: AccountDetailsEntity,
        incomeCategories: List<AccountCategoryDetailsEntity>,
        expenseCategories: List<AccountCategoryDetailsEntity>
    ): AccountDetailsModel {
        return AccountDetailsModel(
            id = accountDetails.id,
            name = accountDetails.name,
            balance = accountDetails.balance,
            currency = accountDetails.currency,
            incomeCategories = incomeCategories.map { mapCategoryEntityToDomain(it) },
            expenseCategories = expenseCategories.map { mapCategoryEntityToDomain(it) },
            createdAt = Date(accountDetails.createdAt),
            updatedAt = Date(accountDetails.updatedAt)
        )
    }

    fun mapDomainToEntity(model: AccountDetailsModel): AccountDetailsEntity {
        return AccountDetailsEntity(
            id = model.id,
            name = model.name,
            balance = model.balance,
            currency = model.currency,
            createdAt = model.createdAt.time,
            updatedAt = model.updatedAt.time
        )
    }

    fun mapDomainToCategories(accountId: Int, model: AccountDetailsModel): List<AccountCategoryDetailsEntity> {
        val incomeCategories = model.incomeCategories.map { category ->
            AccountCategoryDetailsEntity(
                accountId = accountId,
                categoryId = category.id,
                categoryName = category.name,
                emoji = category.emoji,
                amount = category.amount,
                isIncome = true
            )
        }

        val expenseCategories = model.expenseCategories.map { category ->
            AccountCategoryDetailsEntity(
                accountId = accountId,
                categoryId = category.id,
                categoryName = category.name,
                emoji = category.emoji,
                amount = category.amount,
                isIncome = false
            )
        }

        return incomeCategories + expenseCategories
    }

    private fun mapCategoryEntityToDomain(entity: AccountCategoryDetailsEntity): CategoryDetailsModel {
        return CategoryDetailsModel(
            id = entity.categoryId,
            name = entity.categoryName,
            emoji = entity.emoji,
            amount = entity.amount
        )
    }
}