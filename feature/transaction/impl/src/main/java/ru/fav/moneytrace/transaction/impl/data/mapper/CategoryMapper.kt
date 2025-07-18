package ru.fav.moneytrace.transaction.impl.data.mapper

import ru.fav.moneytrace.transaction.api.model.AccountStateModel
import ru.fav.moneytrace.transaction.api.model.CategoryModel
import ru.fav.moneytrace.transaction.impl.data.remote.pojo.response.CategoryResponse
import javax.inject.Inject

class CategoryMapper @Inject constructor() {
     fun mapNetworkToDomain(input: CategoryResponse?): CategoryModel {
        return input?.let {
            CategoryModel (
                id = it.id ?: 0,
                name = it.name.orEmpty(),
                emoji = it.emoji.orEmpty(),
                isIncome = it.isIncome ?: false
            )
        } ?: CategoryModel()
    }

    fun mapEntityToDomain(
        id: Int,
        name: String,
        emoji: String,
        isIncome: Boolean
    ): CategoryModel {
        return CategoryModel(
            id = id,
            name = name,
            emoji = emoji,
            isIncome = isIncome
        )
    }
}