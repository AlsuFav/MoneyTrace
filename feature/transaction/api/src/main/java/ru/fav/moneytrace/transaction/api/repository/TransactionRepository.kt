package ru.fav.moneytrace.transaction.api.repository

import ru.fav.moneytrace.transaction.api.model.TransactionModel
import ru.fav.moneytrace.util.result.Result

interface TransactionRepository {
    suspend fun getTransactionsByAccountAndPeriod(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    ): Result<List<TransactionModel>>
}