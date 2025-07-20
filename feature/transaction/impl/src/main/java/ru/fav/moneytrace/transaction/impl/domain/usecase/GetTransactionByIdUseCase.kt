package ru.fav.moneytrace.transaction.impl.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.moneytrace.domain.di.qualifier.IoDispatchers
import ru.fav.moneytrace.transaction.api.model.TransactionModel
import ru.fav.moneytrace.transaction.api.repository.TransactionRepository
import ru.fav.moneytrace.util.result.Result
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(id: Int): Result<TransactionModel> {
        return withContext(dispatcher) {
            when (val result = transactionRepository.getTransactionById(id)) {
                is Result.Success -> {
                    val transaction = result.data
                    Result.Success(transaction, cached = result.cached)
                }

                is Result.Failure -> result
            }
        }
    }
}