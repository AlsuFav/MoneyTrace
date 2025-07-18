package ru.fav.moneytrace.analysis.impl.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.moneytrace.account.api.repository.AccountRepository
import ru.fav.moneytrace.analysis.impl.domain.model.CategoryWithPercentageModel
import ru.fav.moneytrace.domain.di.qualifier.IoDispatchers
import ru.fav.moneytrace.transaction.api.model.TransactionModel
import ru.fav.moneytrace.transaction.api.model.TransactionType
import ru.fav.moneytrace.transaction.api.repository.TransactionRepository
import ru.fav.moneytrace.util.DateHelper
import javax.inject.Inject
import ru.fav.moneytrace.util.result.Result
import java.util.Date

class GetCategoriesWithSortedPercentageUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        startDate: Date,
        endDate: Date,
        transactionType: TransactionType = TransactionType.ANY
    ): Result<List<CategoryWithPercentageModel>> {
        return withContext(dispatcher) {
            when (val accountsResult = accountRepository.getAllAccounts()) {
                is Result.Success -> {
                    val transactions = mutableListOf<TransactionModel>()

                    val account = accountsResult.data[0]

                    val currency = when (val detailsResult = accountRepository.getAccountDetails(account.id)) {
                        is Result.Success -> detailsResult.data.currency
                        is Result.Failure -> return@withContext detailsResult
                    }

                    val formattedStartDate = DateHelper.dateToApiFormat(startDate)
                    val formattedEndDate = DateHelper.dateToApiFormat(endDate)

                    when (val transactionsResult =
                        transactionRepository.getTransactionsByAccountAndPeriod(
                            accountId = account.id,
                            startDate = formattedStartDate,
                            endDate = formattedEndDate
                        )) {
                        is Result.Success -> {
                            transactions.addAll(transactionsResult.data)
                        }

                        is Result.Failure -> return@withContext transactionsResult
                    }

                    val filteredTransactions = transactions.filter { transaction ->
                        when (transactionType) {
                            TransactionType.INCOME -> transaction.category.isIncome
                            TransactionType.EXPENSE -> !transaction.category.isIncome
                            TransactionType.ANY -> true
                        }
                    }

                    val categoryGroups = filteredTransactions
                        .groupBy { it.category.id }
                        .mapNotNull { (_, categoryTransactions) ->
                            val firstTransaction = categoryTransactions.first()
                            val totalAmount = categoryTransactions.sumOf { it.amount }

                            if (totalAmount > 0) {
                                Pair(firstTransaction.category, totalAmount)
                            } else null
                        }

                    val totalAmount = categoryGroups.sumOf { it.second }

                    val categoriesWithPercentage = categoryGroups.map { (category, amount) ->
                        val percentage = if (totalAmount > 0) {
                            (amount / totalAmount) * 100
                        } else {
                            0.0
                        }

                        CategoryWithPercentageModel(
                            id = category.id,
                            name = category.name,
                            emoji = category.emoji,
                            amount = amount,
                            currency = currency,
                            percentage = percentage,
                        )
                    }.sortedByDescending { it.amount }

                    Result.Success(categoriesWithPercentage)
                }

                is Result.Failure -> accountsResult
            }
        }
    }
}