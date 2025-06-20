package ru.fav.moneytrace.expenses.ui.nav

import ru.fav.moneytrace.navigation.NavGraph

class ExpensesNavGraph : NavGraph() {
    override val route = "expenses"
    override val startDestination = "expenses_today_screen"
    val historyDestination = "expenses_history_screen"
}