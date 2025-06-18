package ru.fav.moneytrace.domain.usecase

import ru.fav.moneytrace.domain.model.Transaction
import javax.inject.Inject

class GetTransactionTotalSumUseCase @Inject constructor() {
    operator fun invoke(transactions: List<Transaction>): String {
        return transactions
            .sumOf { it.amount.toLongOrNull() ?: 0 }
            .toString()
    }
}