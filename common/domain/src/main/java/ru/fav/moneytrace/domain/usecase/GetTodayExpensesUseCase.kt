package ru.fav.moneytrace.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.moneytrace.domain.di.qualifier.IoDispatchers
import ru.fav.moneytrace.domain.model.Transaction
import ru.fav.moneytrace.domain.provider.DateProvider
import ru.fav.moneytrace.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTodayExpensesUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val dateProvider: DateProvider,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): List<Transaction> {
        return withContext(dispatcher) {
            val today = dateProvider.formatDate(dateProvider.getCurrentDate())
            transactionRepository.getTransactionsByPeriod(today, today)
                .filter { !it.isIncome }
        }
    }
}