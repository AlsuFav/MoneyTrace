package ru.fav.moneytrace.analysis.impl.ui.screen.state

import androidx.compose.runtime.Immutable
import ru.fav.moneytrace.analysis.impl.ui.model.CategoryAnalysisUIModel
import ru.fav.moneytrace.util.DateHelper

@Immutable
data class AnalysisState(
    val isLoading: Boolean = false,
    val startDate: String = DateHelper.getCurrentMonthStartDisplayFormat(),
    val endDate: String = DateHelper.getTodayDisplayFormat(),
    val total: String = "0.00 ₽",
    val categories: List<CategoryAnalysisUIModel> = emptyList(),
    val showStartDatePicker: Boolean = false,
    val showEndDatePicker: Boolean = false,
    val showErrorDialog: String? = null
)