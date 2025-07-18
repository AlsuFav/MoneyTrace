package ru.fav.moneytrace.transaction.impl.data.mapper

import ru.fav.moneytrace.transaction.api.model.TransactionModel
import ru.fav.moneytrace.transaction.impl.data.local.entity.TransactionEntity
import ru.fav.moneytrace.transaction.impl.data.remote.pojo.response.TransactionResponse
import ru.fav.moneytrace.util.DateHelper
import java.util.Date
import javax.inject.Inject

class TransactionMapper @Inject constructor(
    private val accountStateMapper: AccountStateMapper,
    private val categoryMapper: CategoryMapper,
) {

    fun mapNetworkToDomain(input: TransactionResponse?): TransactionModel {
        return input?.let {
            TransactionModel(
                id = input.id ?: 0,
                account = accountStateMapper.mapNetworkToDomain(input.account),
                category = categoryMapper.mapNetworkToDomain(input.category),
                amount = input.amount?.toDoubleOrNull() ?: 0.0,
                transactionDate = input.transactionDate?.let { DateHelper.parseApiDateTime(it) } ?: Date(),
                comment = input.comment.orEmpty(),
                createdAt = input.createdAt?.let { DateHelper.parseApiDateTime(it) } ?: Date(),
                updatedAt = input.updatedAt?.let { DateHelper.parseApiDateTime(it) } ?: Date(),
            )
        } ?: TransactionModel()
    }

    fun mapEntityToDomain(input: TransactionEntity): TransactionModel {
        return TransactionModel(
            id = input.id,
            account = accountStateMapper.mapEntityToDomain(
                accountId = input.accountId,
                accountName = input.accountName,
                accountBalance = input.accountBalance,
                accountCurrency = input.accountCurrency
            ),
            category = categoryMapper.mapEntityToDomain(
                id = input.categoryId,
                name = input.categoryName,
                emoji = input.categoryEmoji,
                isIncome = input.categoryIsIncome
            ),
            amount = input.amount,
            transactionDate = Date(input.transactionDate),
            comment = input.comment,
            createdAt = Date(input.createdAt),
            updatedAt = Date(input.updatedAt)
        )
    }

    fun mapDomainToEntity(model: TransactionModel): TransactionEntity {
        return TransactionEntity(
            id = model.id,
            accountId = model.account.id,
            accountName = model.account.name,
            accountBalance = model.account.balance,
            accountCurrency = model.account.currency,
            categoryId = model.category.id,
            categoryName = model.category.name,
            categoryEmoji = model.category.emoji,
            categoryIsIncome = model.category.isIncome,
            amount = model.amount,
            transactionDate = model.transactionDate.time,
            comment = model.comment,
            createdAt = model.createdAt.time,
            updatedAt = model.updatedAt.time
        )
    }

    fun mapNetworkToDomainList(input: List<TransactionResponse>?): List<TransactionModel> {
        return input?.map { mapNetworkToDomain(it) } ?: emptyList()
    }

    fun mapEntityToDomainList(input: List<TransactionEntity>): List<TransactionModel> {
        return input.map { mapEntityToDomain(it) }
    }

    fun mapDomainToEntityList(models: List<TransactionModel>): List<TransactionEntity> {
        return models.map { mapDomainToEntity(it) }
    }
}