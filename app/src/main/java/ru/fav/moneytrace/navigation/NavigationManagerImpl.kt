package ru.fav.moneytrace.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ru.fav.moneytrace.account.ui.AccountFeature
import ru.fav.moneytrace.expenses.ui.ExpensesNav
import ru.fav.moneytrace.income.ui.IncomeNav
import ru.fav.moneytrace.settings.ui.SettingsNav
import ru.fav.moneytrace.stats.ui.StatsNav
import javax.inject.Inject

class NavigationManagerImpl @Inject constructor() : NavigationManager {

    val appFeatures: List<FeatureNav> = listOf(
        AccountFeature,
        SettingsNav,
        IncomeNav,
        ExpensesNav,
        StatsNav
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
        AccountFeature.navigate(navController)
    }

    override fun navigateToStats(navController: NavController) {
        StatsNav.navigate(navController)
    }

    override fun navigateToExpenses(navController: NavController) {
        ExpensesNav.navigate(navController)
    }
}