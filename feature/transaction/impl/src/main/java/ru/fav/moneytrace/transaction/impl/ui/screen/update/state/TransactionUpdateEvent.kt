package ru.fav.moneytrace.transaction.impl.ui.screen.update.state

import ru.fav.moneytrace.transaction.api.model.TransactionType
import ru.fav.moneytrace.transaction.impl.ui.model.CategoryUIModel
import java.util.Date

sealed class TransactionUpdateEvent {
    object ShowDatePicker : TransactionUpdateEvent()
    object ShowTimePicker : TransactionUpdateEvent()
    object HideDatePicker : TransactionUpdateEvent()
    object HideTimePicker : TransactionUpdateEvent()
    data class  OnDeleteClicked(val transactionId: Int) : TransactionUpdateEvent()
    data class OnDateSelected(val date: Date) : TransactionUpdateEvent()
    data class OnTimeSelected(val time: Date) : TransactionUpdateEvent()
    data class LoadData(val transactionId: Int, val transactionType: TransactionType) : TransactionUpdateEvent()
    object OnDoneClicked : TransactionUpdateEvent()
    object HideErrorDialog : TransactionUpdateEvent()
    object HideCategoryBottomSheet : TransactionUpdateEvent()
    object ShowCategoryBottomSheet : TransactionUpdateEvent()
    data class SelectCategory(val category: CategoryUIModel) : TransactionUpdateEvent()
    data class UpdateComment(val comment: String) : TransactionUpdateEvent()
    data class UpdateAmount(val amount: String) : TransactionUpdateEvent()
}