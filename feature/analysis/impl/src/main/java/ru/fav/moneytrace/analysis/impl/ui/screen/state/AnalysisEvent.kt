package ru.fav.moneytrace.analysis.impl.ui.screen.state

import ru.fav.moneytrace.transaction.api.model.TransactionType
import java.util.Date

sealed class AnalysisEvent {
    object ShowStartDatePicker : AnalysisEvent()
    object ShowEndDatePicker : AnalysisEvent()
    object HideDatePicker : AnalysisEvent()
    data class OnStartDateSelected(val date: Date, val transactionType: TransactionType) : AnalysisEvent()
    data class OnEndDateSelected(val date: Date, val transactionType: TransactionType) : AnalysisEvent()
    data class LoadAnalysis(val transactionType: TransactionType) : AnalysisEvent()
    object HideErrorDialog : AnalysisEvent()
}