package ru.fav.moneytrace.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ru.fav.moneytrace.account.impl.ui.nav.AccountNav
import ru.fav.moneytrace.categories.impl.ui.nav.CategoriesNav
import ru.fav.moneytrace.expenses.impl.ui.nav.ExpensesNav
import ru.fav.moneytrace.income.impl.ui.nav.IncomeNav
import ru.fav.moneytrace.settings.impl.ui.nav.SettingsNav
import javax.inject.Inject

class NavigationManagerImpl @Inject constructor() : NavigationManager {

    val appFeatures: List<FeatureNav> = listOf(
        AccountNav,
        SettingsNav,
        IncomeNav,
        ExpensesNav,
        CategoriesNav
    )

    override fun buildNavGraph(
        builder: NavGraphBuilder,
        navController: NavController,
    ) {
        appFeatures.forEach { feature ->
            feature.routes(builder, navController, this)
        }
    }

    override fun navigateToSettings(navController: NavController) {
        SettingsNav.navigate(navController)
    }

    override fun navigateToIncome(navController: NavController) {
        IncomeNav.navigate(navController)
    }

    override fun navigateToAccount(navController: NavController) {
        AccountNav.navigate(navController)
    }

    override fun navigateToCategories(navController: NavController) {
        CategoriesNav.navigate(navController)
    }

    override fun navigateToExpenses(navController: NavController) {
        ExpensesNav.navigate(navController)
    }

    override fun navigateBack(navController: NavController) {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        }
    }
}