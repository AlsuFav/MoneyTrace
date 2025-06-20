package ru.fav.moneytrace.data.mapper

import ru.fav.moneytrace.domain.model.CategoryDetailsModel
import ru.fav.moneytrace.domain.model.TransactionModel
import ru.fav.moneytrace.network.pojo.response.CategoryDetailsResponse
import ru.fav.moneytrace.network.pojo.response.TransactionResponse
import ru.fav.moneytrace.util.DateHelper
import java.util.Date
import javax.inject.Inject

class TransactionMapper @Inject constructor(
    private val accountStateMapper: AccountStateMapper,
    private val categoryMapper: CategoryMapper,
) {

    fun map(input: TransactionResponse?): TransactionModel {
        return input?.let {
            TransactionModel (
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

    fun mapList(input: List<TransactionResponse>?): List<TransactionModel> {
        return input?.map { map(it) } ?: emptyList()
    }
}