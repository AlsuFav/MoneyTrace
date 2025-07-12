package ru.fav.moneytrace.transaction.impl.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.moneytrace.domain.di.qualifier.IoDispatchers
import ru.fav.moneytrace.transaction.api.model.TransactionModel
import ru.fav.moneytrace.transaction.api.repository.TransactionRepository
import ru.fav.moneytrace.util.DateHelper
import ru.fav.moneytrace.util.result.Result
import java.util.Date
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: Date,
        comment: String?,
    ): Result<TransactionModel> {
        return withContext(dispatcher) {
            when (val result = transactionRepository.createTransaction(
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
                transactionDate = DateHelper.dateTimeToApiFormat(transactionDate),
                comment = comment,
            )) {
                is Result.Success -> {
                    val transaction = result.data
                    Result.Success(transaction)
                }

                is Result.Failure -> result
            }
        }
    }
}