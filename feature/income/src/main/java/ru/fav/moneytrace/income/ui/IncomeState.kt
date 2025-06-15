package ru.fav.moneytrace.income.ui

import androidx.compose.runtime.Immutable
import ru.fav.moneytrace.domain.model.Transaction

@Immutable
data class IncomeState(
    val total: String = "",
    val currency: String = "",
    val income: List<Transaction> = emptyList()
)