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

class DeleteTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(id: Int): Result<Unit> {
        return withContext(dispatcher) {
            when (val result = transactionRepository.deleteTransactionById(id = id)) {
                is Result.Success -> {
                    Result.Success(result.data, cached = result.cached)
                }

                is Result.Failure -> result
            }
        }
    }
}