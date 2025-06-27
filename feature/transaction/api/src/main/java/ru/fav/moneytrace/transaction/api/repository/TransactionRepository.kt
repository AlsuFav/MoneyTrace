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
}