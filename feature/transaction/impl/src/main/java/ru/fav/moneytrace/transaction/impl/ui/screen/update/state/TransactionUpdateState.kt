package ru.fav.moneytrace.transaction.impl.ui.screen.update.state

import androidx.compose.runtime.Immutable
import ru.fav.moneytrace.transaction.impl.ui.model.CategoryUIModel
import ru.fav.moneytrace.transaction.impl.ui.model.TransactionUIModel

@Immutable
data class TransactionUpdateState(
    val isLoading: Boolean = false,
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,
    val showErrorDialog: String? = null,
    val transaction: TransactionUIModel = TransactionUIModel(),
    val categories: List<CategoryUIModel> = emptyList(),
    val showBottomSheet: Boolean = false
)