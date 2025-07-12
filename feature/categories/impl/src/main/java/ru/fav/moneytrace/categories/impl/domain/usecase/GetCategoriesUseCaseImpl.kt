package ru.fav.moneytrace.categories.impl.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.moneytrace.account.api.model.CategoryDetailsModel
import ru.fav.moneytrace.categories.api.repository.CategoryRepository
import ru.fav.moneytrace.categories.api.usecase.GetCategoriesUseCase
import ru.fav.moneytrace.domain.di.qualifier.IoDispatchers
import ru.fav.moneytrace.transaction.api.model.CategoryModel
import ru.fav.moneytrace.transaction.api.model.TransactionType
import ru.fav.moneytrace.util.result.FailureReason
import ru.fav.moneytrace.util.result.Result
import javax.inject.Inject


class GetCategoriesUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) : GetCategoriesUseCase {
    override suspend operator fun invoke(transactionType: TransactionType): Result<List<CategoryModel>> {
        return withContext(dispatcher) {
            when (transactionType) {
                TransactionType.INCOME -> categoryRepository.getCategoriesByType(isIncome = true)
                TransactionType.EXPENSE -> categoryRepository.getCategoriesByType(isIncome = false)
                TransactionType.ANY -> {
                    val incomeResult = categoryRepository.getCategoriesByType(isIncome = true)
                    val expenseResult = categoryRepository.getCategoriesByType(isIncome = false)

                    when {
                        incomeResult is Result.Success && expenseResult is Result.Success -> {
                            Result.Success(incomeResult.data + expenseResult.data)
                        }
                        incomeResult is Result.Failure -> incomeResult
                        expenseResult is Result.Failure -> expenseResult
                        else -> Result.Failure(FailureReason.Unknown())
                    }
                }
            }
        }
    }
}