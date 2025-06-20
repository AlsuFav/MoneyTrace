package ru.fav.moneytrace.categories.ui.mapper

import ru.fav.moneytrace.categories.ui.model.CategoryDetailsUIModel
import ru.fav.moneytrace.domain.model.AccountModel
import ru.fav.moneytrace.domain.model.CategoryDetailsModel
import ru.fav.moneytrace.util.extension.formatAmount
import ru.fav.moneytrace.util.extension.toCurrencySymbol
import javax.inject.Inject

class CategoryDetailsUIMapper @Inject constructor() {
    fun map(category: CategoryDetailsModel): CategoryDetailsUIModel {
        return CategoryDetailsUIModel(
            id = category.id,
            name = category.name,
            emoji = category.emoji
        )
    }

    fun mapList(categories: List<CategoryDetailsModel>): List<CategoryDetailsUIModel> {
        return categories.map { map(it) }
    }
}