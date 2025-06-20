package ru.fav.moneytrace.expenses.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.moneytrace.domain.di.qualifier.IoDispatchers
import ru.fav.moneytrace.domain.model.TransactionModel
import ru.fav.moneytrace.domain.repository.AccountRepository
import ru.fav.moneytrace.domain.repository.TransactionRepository
import ru.fav.moneytrace.util.result.Result
import ru.fav.moneytrace.util.DateHelper
import javax.inject.Inject

class GetTodayExpensesUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Result<List<TransactionModel>> {
        return withContext(dispatcher) {
            when (val accountsResult = accountRepository.getAllAccounts()) {
                is Result.Success -> {
                    val transactions = mutableListOf<TransactionModel>()

                    val account = accountsResult.data[0]

                    val todayDate = DateHelper.getTodayApiFormat()

                    when (val transactionsResult =
                        transactionRepository.getTransactionsByAccountAndPeriod(
                            accountId = account.id,
                            startDate = todayDate,
                            endDate = todayDate
                        )) {
                        is Result.Success -> {
                            transactions.addAll(transactionsResult.data)
                        }

                        is Result.NetworkError -> return@withContext transactionsResult
                        is Result.HttpError -> return@withContext transactionsResult
                    }

                    val filteredCategories = transactions.filter { transaction ->
                        !transaction.category.isIncome
                    }.sortedBy {
                        transaction -> transaction.transactionDate
                    }.reversed()

                    Result.Success(filteredCategories)
                }

                is Result.NetworkError -> accountsResult
                is Result.HttpError -> accountsResult
            }
        }
    }
}