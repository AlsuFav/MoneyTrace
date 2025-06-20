package ru.fav.moneytrace.domain.repository

import ru.fav.moneytrace.domain.model.TransactionModel
import ru.fav.moneytrace.util.result.Result

interface TransactionRepository {
    suspend fun getTransactionsByAccountAndPeriod(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    ): Result<List<TransactionModel>>
}