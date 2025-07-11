package ru.fav.moneytrace.transaction.impl.ui.mapper

import ru.fav.moneytrace.transaction.api.model.TransactionModel
import ru.fav.moneytrace.transaction.impl.ui.model.AccountUIModel
import ru.fav.moneytrace.transaction.impl.ui.model.CategoryUIModel
import ru.fav.moneytrace.transaction.impl.ui.model.TransactionUIModel
import ru.fav.moneytrace.ui.util.extension.formatAmount
import ru.fav.moneytrace.ui.util.extension.toCurrencySymbol
import ru.fav.moneytrace.util.DateHelper
import javax.inject.Inject

class TransactionUIMapper @Inject constructor(
    private val categoryUIMapper: CategoryUIMapper
) {
    fun map(transaction: TransactionModel): TransactionUIModel {
        return TransactionUIModel(
            id = transaction.id,
            account = AccountUIModel(
                id = transaction.account.id,
                name = transaction.account.name,
                currencySymbol = transaction.account.currency.toCurrencySymbol()
            ),
            category = categoryUIMapper.map(transaction.category),
            comment = transaction.comment,
            amount = transaction.amount.formatAmount(),
            date = DateHelper.formatDateForDisplay(transaction.transactionDate) ?: DateHelper.getTodayDisplayFormat(),
            time = DateHelper.formatTimeForDisplay(transaction.transactionDate) ?: DateHelper.getCurrentTimeDisplayFormat()
        )
    }
}