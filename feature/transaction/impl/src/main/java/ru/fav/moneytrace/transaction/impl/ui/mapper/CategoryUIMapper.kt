package ru.fav.moneytrace.transaction.impl.ui.mapper

import ru.fav.moneytrace.account.api.model.CategoryDetailsModel
import ru.fav.moneytrace.transaction.api.model.CategoryModel
import ru.fav.moneytrace.transaction.impl.ui.model.CategoryUIModel
import javax.inject.Inject

class CategoryUIMapper @Inject constructor() {
    fun map(category: CategoryModel): CategoryUIModel {
        return CategoryUIModel(
            id = category.id,
            name = category.name,
            emoji = category.emoji
        )
    }

    fun mapList(categories: List<CategoryModel>): List<CategoryUIModel> {
        return categories.map { map(it) }
    }
}