package ru.fav.moneytrace.categories.api.repository

import ru.fav.moneytrace.util.result.Result
import ru.fav.moneytrace.transaction.api.model.CategoryModel

interface CategoryRepository {
    suspend fun getCategoriesByType(isIncome: Boolean = false): Result<List<CategoryModel>>
}