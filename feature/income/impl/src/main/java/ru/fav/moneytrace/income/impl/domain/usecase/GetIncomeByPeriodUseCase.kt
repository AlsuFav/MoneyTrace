package ru.fav.moneytrace.income.impl.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.moneytrace.account.api.repository.AccountRepository
import ru.fav.moneytrace.domain.di.qualifier.IoDispatchers
import ru.fav.moneytrace.transaction.api.model.TransactionModel
import ru.fav.moneytrace.transaction.api.repository.TransactionRepository
import ru.fav.moneytrace.util.result.Result
import ru.fav.moneytrace.util.DateHelper
import java.util.Date
import javax.inject.Inject

/**
 * Use case для получения доходов за указанный период.
 *
 * Загружает транзакции основного счета за заданный период, фильтрует только доходы
 * (исключая расходы) и сортирует их по дате в убывающем порядке.
 *
 * @param accountRepository Репозиторий для доступа к данным счетов
 * @param transactionRepository Репозиторий для доступа к данным транзакций
 * @param dispatcher Диспетчер для выполнения операций в фоновом потоке
 */


class GetIncomeByPeriodUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(startDate: Date, endDate: Date): Result<List<TransactionModel>> {
        return withContext(dispatcher) {
            when (val accountsResult = accountRepository.getAllAccounts()) {
                is Result.Success -> {
                    val transactions = mutableListOf<TransactionModel>()

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
                            transactions.addAll(transactionsResult.data)
                        }

                        is Result.Failure -> return@withContext transactionsResult
                    }

                    val filteredCategories = transactions.filter { transaction ->
                        transaction.category.isIncome
                    }.sortedBy {
                        transaction -> transaction.transactionDate
                    }.reversed()

                    Result.Success(filteredCategories)
                }

                is Result.Failure -> accountsResult
            }
        }
    }
}