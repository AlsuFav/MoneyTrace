package ru.fav.moneytrace.expenses.ui.screen.history.state

import java.util.Date

sealed class ExpensesHistoryEvent {
    object ShowStartDatePicker : ExpensesHistoryEvent()
    object ShowEndDatePicker : ExpensesHistoryEvent()
    object HideDatePicker : ExpensesHistoryEvent()
    data class OnStartDateSelected(val date: Date) : ExpensesHistoryEvent()
    data class OnEndDateSelected(val date: Date) : ExpensesHistoryEvent()
    object LoadExpenses : ExpensesHistoryEvent()
    object HideErrorDialog : ExpensesHistoryEvent()
}