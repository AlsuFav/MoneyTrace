package ru.fav.moneytrace.categories.impl.ui.state

sealed class CategoriesEvent {
    data class OnInputChanged(val input: String) : CategoriesEvent()
    object OnSearch : CategoriesEvent()
    object LoadCategories : CategoriesEvent()
    object HideErrorDialog : CategoriesEvent()
}
