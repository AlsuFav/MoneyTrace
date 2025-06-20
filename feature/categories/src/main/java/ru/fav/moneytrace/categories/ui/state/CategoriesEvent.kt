package ru.fav.moneytrace.categories.ui.state

sealed class CategoriesEvent {
    data class OnInputChanged(val input: String) : CategoriesEvent()
    object OnSearch : CategoriesEvent()
    object Retry : CategoriesEvent()
    object HideErrorDialog : CategoriesEvent()
}
