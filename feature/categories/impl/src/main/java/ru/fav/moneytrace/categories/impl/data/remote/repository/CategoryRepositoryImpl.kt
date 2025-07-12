package ru.fav.moneytrace.categories.impl.data.remote.repository

import ru.fav.moneytrace.categories.api.repository.CategoryRepository
import ru.fav.moneytrace.categories.impl.data.remote.mapper.CategoryMapper
import ru.fav.moneytrace.categories.impl.data.remote.remote.CategoryApi
import ru.fav.moneytrace.network.util.ApiClient
import ru.fav.moneytrace.transaction.api.model.CategoryModel
import ru.fav.moneytrace.util.result.Result
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryApi: CategoryApi,
    private val categoryMapper: CategoryMapper,
    private val apiClient: ApiClient,
): CategoryRepository {

    override suspend fun getCategoriesByType(isIncome: Boolean): Result<List<CategoryModel>> {
        return when (val result = apiClient.call { categoryApi.getCategoriesByType(isIncome) }) {
            is Result.Success -> {
                val categories = categoryMapper.mapList(result.data)
                Result.Success(categories)
            }
            is Result.Failure -> result
        }
    }
}
