package ru.fav.moneytrace.transaction.impl.data.mapper

import ru.fav.moneytrace.transaction.api.model.CategoryModel
import ru.fav.moneytrace.transaction.impl.data.local.entity.TransactionCategoryEntity
import ru.fav.moneytrace.transaction.impl.data.remote.pojo.response.CategoryResponse
import javax.inject.Inject

class CategoryMapper @Inject constructor() {

    fun map(input: CategoryResponse?): CategoryModel {
        return input?.let {
            CategoryModel(
                id = it.id ?: 0,
                name = it.name.orEmpty(),
                emoji = it.emoji.orEmpty(),
                isIncome = it.isIncome ?: false
            )
        } ?: CategoryModel()
    }

    fun mapEntityToDomain(input: TransactionCategoryEntity): CategoryModel {
        return CategoryModel(
            id = input.id,
            name = input.name,
            emoji = input.emoji,
            isIncome = input.isIncome
        )
    }

    fun mapDomainToEntity(model: CategoryModel): TransactionCategoryEntity {
        return TransactionCategoryEntity(
            id = model.id,
            name = model.name,
            emoji = model.emoji,
            isIncome = model.isIncome
        )
    }
}