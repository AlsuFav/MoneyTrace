package ru.fav.moneytrace.transaction.api.repository

import ru.fav.moneytrace.transaction.api.model.TransactionModel
import ru.fav.moneytrace.util.result.Result

/**
 * Интерфейс репозитория для работы с транзакциями.
 *
 * Предоставляет методы для получения транзакций по идентификатору счета
 * и временному периоду.
 */

interface TransactionRepository {
    suspend fun getTransactionsByAccountAndPeriod(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    ): Result<List<TransactionModel>>

    suspend fun getTransactionById(id: Int) : Result<TransactionModel>

    suspend fun createTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String? = null,
    ) : Result<TransactionModel>

    suspend fun updateTransaction(
        id: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String? = null,
    ) : Result<TransactionModel>

    suspend fun deleteTransactionBuId(id: Int) :  Result<Unit>
}