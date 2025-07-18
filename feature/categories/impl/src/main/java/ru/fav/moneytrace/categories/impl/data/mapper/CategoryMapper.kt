package ru.fav.moneytrace.categories.impl.data.mapper

import ru.fav.moneytrace.categories.impl.data.local.entity.CategoryEntity
import ru.fav.moneytrace.categories.impl.data.remote.pojo.response.CategoryResponse
import ru.fav.moneytrace.transaction.api.model.CategoryModel
import javax.inject.Inject

class CategoryMapper @Inject constructor() {

    fun mapNetworkToDomain(input: CategoryResponse?): CategoryModel {
        return input?.let {
            CategoryModel (
                id = it.id ?: 0,
                name = it.name.orEmpty(),
                emoji = it.emoji.orEmpty(),
                isIncome = input.isIncome ?: false,
            )
        } ?: CategoryModel()
    }

    fun mapEntityToDomain(input: CategoryEntity): CategoryModel {
        return CategoryModel (
                id = input.id,
                name = input.name,
                emoji = input.emoji,
                isIncome = input.isIncome,
            )
    }

    fun mapEntity(model: CategoryModel): CategoryEntity {
        return CategoryEntity(
            id = model.id,
            name = model.name,
            emoji = model.emoji,
            isIncome = model.isIncome
        )
    }

    fun mapNetworkToDomainList(input: List<CategoryResponse>?): List<CategoryModel> {
        return input?.map { mapNetworkToDomain(it) } ?: emptyList()
    }

    fun mapEntityToDomainList(input: List<CategoryEntity>): List<CategoryModel> {
        return input.map { mapEntityToDomain(it) }
    }

    fun mapEntityList(models: List<CategoryModel>): List<CategoryEntity> {
        return models.map { mapEntity(it) }
    }
}