package ru.fav.moneytrace.transaction.api.usecase

import ru.fav.moneytrace.transaction.api.model.TransactionModel
import ru.fav.moneytrace.transaction.api.model.TransactionType
import ru.fav.moneytrace.util.result.Result
import java.util.Date

interface GetTransactionsByPeriodUseCase {
    suspend operator fun invoke(
        startDate: Date,
        endDate: Date,
        transactionType: TransactionType = TransactionType.ANY,
    ): Result<List<TransactionModel>>
}