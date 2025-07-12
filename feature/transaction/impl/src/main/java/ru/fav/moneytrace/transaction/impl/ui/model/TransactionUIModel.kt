package ru.fav.moneytrace.transaction.impl.ui.model

import ru.fav.moneytrace.util.DateHelper

data class TransactionUIModel(
    val id: Int = 0,
    val account: AccountUIModel = AccountUIModel(),
    val category: CategoryUIModel = CategoryUIModel(),
    val amount: String = "",
    val comment: String = "",
    val date: String = DateHelper.getTodayDisplayFormat(),
    val time: String = DateHelper.getCurrentTimeDisplayFormat()
)