package ru.fav.moneytrace.categories.ui.state

import androidx.compose.runtime.Immutable
import ru.fav.moneytrace.categories.ui.model.CategoryDetailsUIModel

@Immutable
data class CategoriesState(
    val isLoading: Boolean = false,
    val input: String = "",
    val categories: List<CategoryDetailsUIModel> = emptyList(),
    val showErrorDialog: String? = null
)
