package ru.fav.moneytrace.income.ui.screen.history.state

import java.util.Date

sealed class IncomeHistoryEvent {
    object ShowStartDatePicker : IncomeHistoryEvent()
    object ShowEndDatePicker : IncomeHistoryEvent()
    object HideDatePicker : IncomeHistoryEvent()
    data class OnStartDateSelected(val date: Date) : IncomeHistoryEvent()
    data class OnEndDateSelected(val date: Date) : IncomeHistoryEvent()
    object Retry : IncomeHistoryEvent()
    object HideErrorDialog : IncomeHistoryEvent()
}