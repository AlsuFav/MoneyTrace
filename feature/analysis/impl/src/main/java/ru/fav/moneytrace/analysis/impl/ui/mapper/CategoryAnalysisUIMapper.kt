package ru.fav.moneytrace.analysis.impl.ui.mapper

import ru.fav.moneytrace.analysis.impl.domain.model.CategoryWithPercentageModel
import ru.fav.moneytrace.analysis.impl.ui.model.CategoryAnalysisUIModel
import ru.fav.moneytrace.ui.util.extension.formatAmount
import ru.fav.moneytrace.ui.util.extension.toCurrencySymbol
import ru.fav.moneytrace.ui.util.extension.toPercentString
import javax.inject.Inject

class CategoryAnalysisUIMapper @Inject constructor() {
    fun map(category: CategoryWithPercentageModel): CategoryAnalysisUIModel {
        return CategoryAnalysisUIModel(
            id = category.id,
            name = category.name,
            amount = "${category.amount.formatAmount()} ${category.currency.toCurrencySymbol()}",
            emoji = category.emoji,
            percentage = category.percentage.toPercentString()
        )
    }

    fun mapList(category: List<CategoryWithPercentageModel>): List<CategoryAnalysisUIModel> {
        return category.map { map(it) }
    }

    fun mapTotal(totalSum: Double, currency: String) : String {
        return "${totalSum.formatAmount()} ${currency.toCurrencySymbol()}"
    }
}