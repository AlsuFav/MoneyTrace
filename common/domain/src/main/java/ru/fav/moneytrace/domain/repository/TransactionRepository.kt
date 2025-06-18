package ru.fav.moneytrace.domain.repository

import ru.fav.moneytrace.domain.model.Transaction

interface TransactionRepository {
    suspend fun getTransactionsByPeriod(
        startDate: String = "",
        endDate: String = ""
    ): List<Transaction>
}