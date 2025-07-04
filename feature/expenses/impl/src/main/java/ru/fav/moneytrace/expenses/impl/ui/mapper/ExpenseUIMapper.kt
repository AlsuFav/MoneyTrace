package ru.fav.moneytrace.expenses.impl.ui.mapper

import ru.fav.moneytrace.expenses.impl.ui.model.ExpenseUIModel
import ru.fav.moneytrace.transaction.api.model.TransactionModel
import ru.fav.moneytrace.ui.util.extension.formatAmount
import ru.fav.moneytrace.ui.util.extension.toCurrencySymbol
import ru.fav.moneytrace.util.DateHelper
import javax.inject.Inject

class ExpenseUIMapper @Inject constructor() {
    fun map(transaction: TransactionModel): ExpenseUIModel {
        return ExpenseUIModel(
            id = transaction.id,
            name = transaction.category.name,
            emoji = transaction.category.emoji,
            comment = transaction.comment,
            amount = "${transaction.amount.formatAmount()} ${transaction.account.currency.toCurrencySymbol()}",
            date = DateHelper.formatDateForDisplay(transaction.transactionDate) ?: "01.01.2025",
            time = DateHelper.formatTimeForDisplay(transaction.transactionDate) ?: "00:00"
        )
    }

    fun mapList(transactions: List<TransactionModel>): List<ExpenseUIModel> {
        return transactions.map { map(it) }
    }

    fun mapTotal(totalSum: Double, currency: String) : String {
        return "${totalSum.formatAmount()} ${currency.toCurrencySymbol()}"
    }
}