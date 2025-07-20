package ru.fav.moneytrace.expenses.impl.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.moneytrace.account.api.repository.AccountRepository
import ru.fav.moneytrace.domain.di.qualifier.IoDispatchers
import ru.fav.moneytrace.transaction.api.model.TransactionModel
import ru.fav.moneytrace.transaction.api.repository.TransactionRepository
import ru.fav.moneytrace.util.result.Result
import ru.fav.moneytrace.util.DateHelper
import javax.inject.Inject

/**
 * Use case для получения расходов за текущий день.
 *
 * Загружает транзакции основного счета за сегодняшний день, фильтрует только расходы
 * (исключая доходы) и сортирует их по времени в убывающем порядке.
 *
 * @param accountRepository Репозиторий для доступа к данным счетов
 * @param transactionRepository Репозиторий для доступа к данным транзакций
 * @param dispatcher Диспетчер для выполнения операций в фоновом потоке
 */

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
                    var cachedTransactions: Boolean

                    val account = accountsResult.data[0]

                    val todayDate = DateHelper.getTodayApiFormat()

                    when (val transactionsResult =
                        transactionRepository.getTransactionsByAccountAndPeriod(
                            accountId = account.id,
                            startDate = todayDate,
                            endDate = todayDate
                        )) {
                        is Result.Success -> {
                            cachedTransactions = transactionsResult.cached
                            transactions.addAll(transactionsResult.data)
                        }

                        is Result.Failure -> return@withContext transactionsResult
                    }

                    val filteredCategories = transactions.filter { transaction ->
                        !transaction.category.isIncome
                    }.sortedBy {
                        transaction -> transaction.transactionDate
                    }.reversed()

                    Result.Success(filteredCategories, cached = accountsResult.cached || cachedTransactions)
                }

                is Result.Failure -> accountsResult
            }
        }
    }
}