package ru.fav.moneytrace.data.mapper

import ru.fav.moneytrace.domain.model.CategoryModel
import ru.fav.moneytrace.network.pojo.response.CategoryResponse
import javax.inject.Inject

class CategoryMapper @Inject constructor() {

     fun map(input: CategoryResponse?): CategoryModel {
        return input?.let {
            CategoryModel (
                id = it.id ?: 0,
                name = it.name.orEmpty(),
                emoji = it.emoji.orEmpty(),
                isIncome = it.isIncome ?: false
            )
        } ?: CategoryModel()
    }
}