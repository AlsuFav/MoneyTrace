package ru.fav.moneytrace.income.ui.nav

import ru.fav.moneytrace.navigation.NavGraph

class IncomeNavGraph : NavGraph() {
    override val route = "income"
    override val startDestination = "income_today_screen"
    val historyDestination = "income_history_screen"
}