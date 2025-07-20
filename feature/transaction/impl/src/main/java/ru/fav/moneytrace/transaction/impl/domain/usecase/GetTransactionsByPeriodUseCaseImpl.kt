package ru.fav.moneytrace.transaction.impl.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.moneytrace.account.api.repository.AccountRepository
import ru.fav.moneytrace.domain.di.qualifier.IoDispatchers
import ru.fav.moneytrace.transaction.api.model.TransactionModel
import ru.fav.moneytrace.transaction.api.model.TransactionType
import ru.fav.moneytrace.transaction.api.repository.TransactionRepository
import ru.fav.moneytrace.transaction.api.usecase.GetTransactionsByPeriodUseCase
import ru.fav.moneytrace.util.result.Result
import ru.fav.moneytrace.util.DateHelper
import java.util.Date
import javax.inject.Inject


class GetTransactionsByPeriodUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) : GetTransactionsByPeriodUseCase {
    override suspend operator fun invoke(
        startDate: Date,
        endDate: Date,
        transactionType: TransactionType
    ): Result<List<TransactionModel>> {
        return withContext(dispatcher) {
            when (val accountsResult = accountRepository.getAllAccounts()) {
                is Result.Success -> {
                    val transactions = mutableListOf<TransactionModel>()
                    var cachedTransactions: Boolean

                    val account = accountsResult.data[0]

                    val startDate = DateHelper.dateToApiFormat(startDate)
                    val endDate = DateHelper.dateToApiFormat(endDate)

                    when (val transactionsResult =
                        transactionRepository.getTransactionsByAccountAndPeriod(
                            accountId = account.id,
                            startDate = startDate,
                            endDate = endDate
                        )) {
                        is Result.Success -> {
                            cachedTransactions = transactionsResult.cached
                            transactions.addAll(transactionsResult.data)
                        }

                        is Result.Failure -> return@withContext transactionsResult
                    }

                    val filteredTransactions = transactions.filter { transaction ->
                        when(transactionType) {
                            TransactionType.INCOME -> transaction.category.isIncome
                            TransactionType.EXPENSE -> !transaction.category.isIncome
                            TransactionType.ANY -> true
                        }
                    }.sortedBy {
                        transaction -> transaction.transactionDate
                    }.reversed()

                    Result.Success(filteredTransactions, cached = accountsResult.cached || cachedTransactions)
                }

                is Result.Failure -> accountsResult
            }
        }
    }
}