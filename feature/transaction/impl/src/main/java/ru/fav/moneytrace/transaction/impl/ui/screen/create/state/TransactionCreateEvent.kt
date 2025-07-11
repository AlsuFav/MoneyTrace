package ru.fav.moneytrace.transaction.impl.ui.screen.create.state

import ru.fav.moneytrace.transaction.api.model.TransactionType
import ru.fav.moneytrace.transaction.impl.ui.model.CategoryUIModel
import java.util.Date

sealed class TransactionCreateEvent {
    object ShowDatePicker : TransactionCreateEvent()
    object ShowTimePicker : TransactionCreateEvent()
    object HideDatePicker : TransactionCreateEvent()
    object HideTimePicker : TransactionCreateEvent()
    data class OnDateSelected(val date: Date) : TransactionCreateEvent()
    data class OnTimeSelected(val time: Date) : TransactionCreateEvent()
    data class LoadData(val transactionType: TransactionType) : TransactionCreateEvent()
    object OnDoneClicked : TransactionCreateEvent()
    object HideErrorDialog : TransactionCreateEvent()
    object HideCategoryBottomSheet : TransactionCreateEvent()
    object ShowCategoryBottomSheet : TransactionCreateEvent()
    data class SelectCategory(val category: CategoryUIModel) : TransactionCreateEvent()
    data class UpdateComment(val comment: String) : TransactionCreateEvent()
    data class UpdateAmount(val amount: String) : TransactionCreateEvent()
}