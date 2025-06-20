package ru.fav.moneytrace.categories.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.moneytrace.domain.di.qualifier.IoDispatchers
import ru.fav.moneytrace.domain.model.CategoryDetailsModel
import ru.fav.moneytrace.domain.repository.AccountRepository
import ru.fav.moneytrace.util.result.Result
import javax.inject.Inject

class GetExpenseCategoriesUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(query: String = ""): Result<List<CategoryDetailsModel>> {
        return withContext(dispatcher) {
            when (val accountsResult = accountRepository.getAllAccounts()) {
                is Result.Success -> {
                    val expenseCategories = mutableListOf<CategoryDetailsModel>()

                    val account = accountsResult.data[0]

                    when (val detailsResult = accountRepository.getAccountDetails(account.id)) {
                        is Result.Success -> {
                            expenseCategories.addAll(detailsResult.data.expenseCategories)
                        }

                        is Result.NetworkError -> return@withContext detailsResult
                        is Result.HttpError -> return@withContext detailsResult
                    }

                    val filteredCategories = if (query.isBlank()) {
                        expenseCategories
                    } else {
                        expenseCategories.filter { category ->
                            category.name.contains(query, ignoreCase = true)
                        }
                    }

                    Result.Success(filteredCategories)
                }

                is Result.NetworkError -> accountsResult
                is Result.HttpError -> accountsResult
            }
        }
    }
}