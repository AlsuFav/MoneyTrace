package ru.fav.moneytrace.navigation

import ru.fav.moneytrace.account.impl.ui.nav.AccountNav
import ru.fav.moneytrace.categories.impl.ui.nav.CategoriesNav
import ru.fav.moneytrace.expenses.impl.ui.nav.ExpensesNav
import ru.fav.moneytrace.income.impl.ui.nav.IncomeNav
import ru.fav.moneytrace.settings.impl.ui.nav.SettingsNav

sealed class BottomNavIds(val id: String) {
    object Account : BottomNavIds(AccountNav.navGraph.route)
    object Income : BottomNavIds(IncomeNav.navGraph.route)
    object Settings : BottomNavIds(SettingsNav.navGraph.route)
    object Expenses : BottomNavIds(ExpensesNav.navGraph.route)
    object Categories : BottomNavIds(CategoriesNav.navGraph.route)
}