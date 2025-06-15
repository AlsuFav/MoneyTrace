package ru.fav.moneytrace.expenses.ui

import androidx.compose.runtime.Immutable
import ru.fav.moneytrace.domain.model.Transaction

@Immutable
data class ExpensesState(
    val total: String = "",
    val currency: String = "",
    val expenses: List<Transaction> = emptyList()
)