package ru.fav.moneytrace.account.impl.ui.update.state

import ru.fav.moneytrace.account.impl.ui.model.CurrencyUIModel

sealed class AccountUpdateEvent {
    object LoadData : AccountUpdateEvent()
    object OnDoneClicked : AccountUpdateEvent()
    object HideErrorDialog : AccountUpdateEvent()
    object HideCurrencyBottomSheet : AccountUpdateEvent()
    object ShowCurrencyBottomSheet : AccountUpdateEvent()
    data class SelectCurrency(val currency: CurrencyUIModel) : AccountUpdateEvent()
}
