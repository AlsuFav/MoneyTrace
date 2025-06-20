package ru.fav.moneytrace.income.ui.screen.today.state

import androidx.compose.runtime.Immutable
import ru.fav.moneytrace.income.ui.model.IncomeUIModel

@Immutable
data class IncomeTodayState(
    val total: String = "0.00 ₽",
    val isLoading: Boolean = false,
    val currency: String = "",
    val income: List<IncomeUIModel> = emptyList(),
    val showErrorDialog: String? = null
)