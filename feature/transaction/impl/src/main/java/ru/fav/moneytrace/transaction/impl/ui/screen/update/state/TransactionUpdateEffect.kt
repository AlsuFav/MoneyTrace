package ru.fav.moneytrace.transaction.impl.ui.screen.update.state

sealed class TransactionUpdateEffect {
    object TransactionUpdated : TransactionUpdateEffect()
    object TransactionDeleted : TransactionUpdateEffect()
    data class ShowToast(val message: String) : TransactionUpdateEffect()
}