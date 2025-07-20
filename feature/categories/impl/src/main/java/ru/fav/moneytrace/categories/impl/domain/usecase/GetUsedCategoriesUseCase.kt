package ru.fav.moneytrace.categories.impl.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.moneytrace.account.api.model.CategoryDetailsModel
import ru.fav.moneytrace.account.api.repository.AccountRepository
import ru.fav.moneytrace.categories.api.usecase.GetCategoriesUseCase
import ru.fav.moneytrace.domain.di.qualifier.IoDispatchers
import ru.fav.moneytrace.transaction.api.model.TransactionType
import ru.fav.moneytrace.util.result.Result
import javax.inject.Inject


class GetUsedCategoriesUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(transactionType: TransactionType, query: String): Result<List<CategoryDetailsModel>> {
        return withContext(dispatcher) {
            when (val accountsResult = accountRepository.getAllAccounts()) {
                is Result.Success -> {
                    val categories = mutableListOf<CategoryDetailsModel>()
                    var cachedCategories: Boolean

                    val account = accountsResult.data[0]

                    when (val detailsResult = accountRepository.getAccountDetails(account.id)) {
                        is Result.Success -> {
                            cachedCategories = detailsResult.cached
                            when(transactionType) {
                                TransactionType.EXPENSE -> categories.addAll(detailsResult.data.expenseCategories)
                                TransactionType.INCOME -> categories.addAll(detailsResult.data.incomeCategories)
                                else -> {
                                    categories.addAll(detailsResult.data.expenseCategories)
                                    categories.addAll(detailsResult.data.incomeCategories)
                                }
                            }

                        }

                        is Result.Failure -> return@withContext detailsResult
                    }

                    val filteredCategories = if (query.isBlank()) {
                        categories
                    } else {
                        categories.filter { category ->
                            category.name.contains(query, ignoreCase = true)
                        }
                    }

                    Result.Success(filteredCategories, cached = accountsResult.cached || cachedCategories)
                }

                is Result.Failure -> accountsResult
            }
        }
    }
}