package ru.fav.moneytrace.income.ui.screen.history.state

import androidx.compose.runtime.Immutable
import ru.fav.moneytrace.income.ui.model.IncomeUIModel
import ru.fav.moneytrace.util.DateHelper

@Immutable
data class IncomeHistoryState(
    val isLoading: Boolean = false,
    val startDate: String = DateHelper.getCurrentMonthStartDisplayFormat(),
    val endDate: String = DateHelper.getTodayDisplayFormat(),
    val total: String = "0.00 ₽",
    val income: List<IncomeUIModel> = emptyList(),
    val showStartDatePicker: Boolean = false,
    val showEndDatePicker: Boolean = false,
    val showErrorDialog: String? = null
)