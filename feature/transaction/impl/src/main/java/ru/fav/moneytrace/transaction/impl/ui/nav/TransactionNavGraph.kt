package ru.fav.moneytrace.transaction.impl.ui.nav

import ru.fav.moneytrace.navigation.NavGraph
import ru.fav.moneytrace.transaction.api.model.TransactionType

class TransactionNavGraph : NavGraph() {
    override val route = "transaction"
    override val startDestination = "transaction_main_screen"

    val createDestination = "transaction_create_screen/{transactionType}"
    val updateDestination = "transaction_update_screen/{transactionId}/{transactionType}"

    fun createRoute(transactionType: TransactionType) = "transaction_create_screen/${transactionType.name}"
    fun updateRoute(transactionId: Int, transactionType: TransactionType) =
        "transaction_update_screen/$transactionId/${transactionType.name}"
}