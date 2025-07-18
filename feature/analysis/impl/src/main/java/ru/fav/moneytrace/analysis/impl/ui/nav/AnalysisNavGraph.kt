package ru.fav.moneytrace.analysis.impl.ui.nav

import ru.fav.moneytrace.navigation.NavGraph
import ru.fav.moneytrace.transaction.api.model.TransactionType

class AnalysisNavGraph : NavGraph() {
    override val route = "analysis"
    override val startDestination = "analysis_main_screen/{transactionType}"

    fun mainRoute(transactionType: TransactionType) = "analysis_main_screen/${transactionType.name}"
}