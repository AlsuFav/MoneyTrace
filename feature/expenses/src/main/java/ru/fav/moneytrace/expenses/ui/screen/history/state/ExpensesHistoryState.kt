package ru.fav.moneytrace.expenses.ui.screen.history.state

import androidx.compose.runtime.Immutable
import ru.fav.moneytrace.expenses.ui.model.ExpenseUIModel
import ru.fav.moneytrace.util.DateHelper

@Immutable
data class ExpensesHistoryState(
    val isLoading: Boolean = false,
    val startDate: String = DateHelper.getCurrentMonthStartDisplayFormat(),
    val endDate: String = DateHelper.getTodayDisplayFormat(),
    val total: String = "0.00 ₽",
    val expenses: List<ExpenseUIModel> = emptyList(),
    val showStartDatePicker: Boolean = false,
    val showEndDatePicker: Boolean = false,
    val showErrorDialog: String? = null
)