package ru.fav.moneytrace.categories.impl.data.repository

import ru.fav.moneytrace.categories.api.repository.CategoryRepository
import ru.fav.moneytrace.categories.impl.data.local.dao.CategoryDao
import ru.fav.moneytrace.categories.impl.data.mapper.CategoryMapper
import ru.fav.moneytrace.categories.impl.data.remote.CategoryApi
import ru.fav.moneytrace.network.util.ApiClient
import ru.fav.moneytrace.transaction.api.model.CategoryModel
import ru.fav.moneytrace.util.result.FailureReason
import ru.fav.moneytrace.util.result.Result
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryApi: CategoryApi,
    private val categoryDao: CategoryDao,
    private val categoryMapper: CategoryMapper,
    private val apiClient: ApiClient,
) : CategoryRepository {

    override suspend fun getCategoriesByType(isIncome: Boolean): Result<List<CategoryModel>> {
        return when (val networkResult = apiClient.call { categoryApi.getCategoriesByType(isIncome) }) {
            is Result.Success -> {
                val domainModels = categoryMapper.mapNetworkToDomainList(networkResult.data)

                val entities = categoryMapper.mapEntityList(domainModels)

                categoryDao.insertAll(entities)

                Result.Success(domainModels)
            }
            is Result.Failure -> {
                if (networkResult.reason is FailureReason.Network) {
                    val cachedCategories = categoryDao.getCategoriesByType(isIncome)

                    if (cachedCategories.isNotEmpty()) {
                        val domainModels = categoryMapper.mapEntityToDomainList(cachedCategories)
                        Result.Success(domainModels, cached = true)
                    } else {
                        networkResult
                    }
                } else {
                    networkResult
                }
            }
        }
    }
}