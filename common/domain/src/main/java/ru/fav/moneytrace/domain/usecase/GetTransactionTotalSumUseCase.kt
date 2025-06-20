package ru.fav.moneytrace.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.moneytrace.domain.di.qualifier.DefaultDispatchers
import ru.fav.moneytrace.domain.di.qualifier.IoDispatchers
import ru.fav.moneytrace.domain.model.TransactionModel
import javax.inject.Inject

class GetTransactionTotalSumUseCase @Inject constructor(
    @DefaultDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(transactions: List<TransactionModel>): Double {
        return withContext(dispatcher) {
            transactions
                .sumOf { it.amount }
        }
    }
}