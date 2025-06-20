package ru.fav.moneytrace.data.mapper

import ru.fav.moneytrace.domain.model.CategoryDetailsModel
import ru.fav.moneytrace.network.pojo.response.CategoryDetailsResponse
import javax.inject.Inject

class CategoryDetailsMapper @Inject constructor() {

    fun map(input: CategoryDetailsResponse?): CategoryDetailsModel {
        return input?.let {
            CategoryDetailsModel (
                id = it.categoryId ?: 0,
                name = it.categoryName.orEmpty(),
                emoji = it.emoji.orEmpty(),
                amount = input.amount?.toDoubleOrNull() ?: 0.0,
            )
        } ?: CategoryDetailsModel()
    }

    fun mapList(input: List<CategoryDetailsResponse>?): List<CategoryDetailsModel> {
        return input?.map { map(it) } ?: emptyList()
    }
}