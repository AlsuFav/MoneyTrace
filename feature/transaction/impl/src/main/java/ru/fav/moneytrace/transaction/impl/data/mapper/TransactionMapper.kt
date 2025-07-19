package ru.fav.moneytrace.transaction.impl.data.mapper

import ru.fav.moneytrace.transaction.api.model.AccountStateModel
import ru.fav.moneytrace.transaction.api.model.CategoryModel
import ru.fav.moneytrace.transaction.api.model.TransactionModel
import ru.fav.moneytrace.transaction.impl.data.local.entity.AccountStateEntity
import ru.fav.moneytrace.transaction.impl.data.local.entity.TransactionCategoryEntity
import ru.fav.moneytrace.transaction.impl.data.local.entity.TransactionEntity
import ru.fav.moneytrace.transaction.impl.data.local.util.TransactionWithJoinData
import ru.fav.moneytrace.transaction.impl.data.remote.pojo.response.TransactionResponse
import ru.fav.moneytrace.util.DateHelper
import java.util.Date
import javax.inject.Inject

class TransactionMapper @Inject constructor(
    private val accountStateMapper: AccountStateMapper,
    private val categoryMapper: CategoryMapper,
) {

    fun map(input: TransactionResponse?): TransactionModel {
        return input?.let {
            TransactionModel(
                id = input.id ?: 0,
                account = accountStateMapper.map(input.account),
                category = categoryMapper.map(input.category),
                amount = input.amount?.toDoubleOrNull() ?: 0.0,
                transactionDate = input.transactionDate?.let { DateHelper.parseApiDateTime(it) } ?: Date(),
                comment = input.comment.orEmpty(),
                createdAt = input.createdAt?.let { DateHelper.parseApiDateTime(it) } ?: Date(),
                updatedAt = input.updatedAt?.let { DateHelper.parseApiDateTime(it) } ?: Date(),
            )
        } ?: TransactionModel()
    }

    fun mapJoinDataToDomain(input: TransactionWithJoinData): TransactionModel {
        val aa= TransactionModel(
            id = input.id,
            account = AccountStateModel(
                id = input.accountId,
                name = input.accountName,
                balance = input.accountBalance,
                currency = input.accountCurrency
            ),
            category = CategoryModel(
                id = input.categoryId,
                name = input.categoryName,
                emoji = input.categoryEmoji,
                isIncome = input.categoryIsIncome
            ),
            amount = input.amount,
            transactionDate = input.transactionDate.let { DateHelper.parseApiDateTime(it) },
            comment = input.comment,
            createdAt = input.createdAt.let { DateHelper.parseApiDateTime(it) },
            updatedAt = input.updatedAt.let { DateHelper.parseApiDateTime(it) }
        )
        println(aa)
        return aa
    }

    fun mapDomainToEntities(model: TransactionModel): Triple<TransactionEntity, AccountStateEntity, TransactionCategoryEntity> {
        val transaction = TransactionEntity(
            id = model.id,
            accountId = model.account.id,
            categoryId = model.category.id,
            amount = model.amount,
            transactionDate = model.transactionDate.let { DateHelper.dateTimeToApiFormat(it) },
            comment = model.comment,
            createdAt = model.createdAt.let { DateHelper.dateTimeToApiFormat(it) },
            updatedAt = model.updatedAt.let { DateHelper.dateTimeToApiFormat(it) },
        )

        val account = accountStateMapper.mapDomainToEntity(model.account)

        val category = TransactionCategoryEntity(
            id = model.category.id,
            name = model.category.name,
            emoji = model.category.emoji,
            isIncome = model.category.isIncome
        )

        return Triple(transaction, account, category)
    }

    fun mapList(input: List<TransactionResponse>?): List<TransactionModel> {
        return input?.map { map(it) } ?: emptyList()
    }

    fun mapJoinDataToDomainList(input: List<TransactionWithJoinData>): List<TransactionModel> {
        return input.map { mapJoinDataToDomain(it) }
    }

    fun mapDomainToEntitiesLists(models: List<TransactionModel>): Triple<List<TransactionEntity>, List<AccountStateEntity>, List<TransactionCategoryEntity>> {
        val transactions = mutableListOf<TransactionEntity>()
        val accounts = mutableListOf<AccountStateEntity>()
        val categories = mutableListOf<TransactionCategoryEntity>()

        models.forEach { model ->
            val (transaction, account, category) = mapDomainToEntities(model)
            transactions.add(transaction)

            if (accounts.none { it.id == account.id }) {
                accounts.add(account)
            }
            if (categories.none { it.id == category.id }) {
                categories.add(category)
            }
        }

        return Triple(transactions, accounts, categories)
    }
}