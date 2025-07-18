package ru.fav.moneytrace.categories.api.usecase

import ru.fav.moneytrace.transaction.api.model.CategoryModel
import ru.fav.moneytrace.transaction.api.model.TransactionType
import ru.fav.moneytrace.util.result.Result

interface GetCategoriesUseCase {
    suspend operator fun invoke(
        transactionType: TransactionType = TransactionType.ANY,
    ): Result<List<CategoryModel>>
}