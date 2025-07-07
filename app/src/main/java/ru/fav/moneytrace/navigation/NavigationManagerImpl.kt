package ru.fav.moneytrace.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import ru.fav.moneytrace.account.impl.ui.nav.AccountNav
import ru.fav.moneytrace.categories.impl.ui.nav.CategoriesNav
import ru.fav.moneytrace.expenses.impl.ui.nav.ExpensesNav
import ru.fav.moneytrace.income.impl.ui.nav.IncomeNav
import ru.fav.moneytrace.settings.impl.ui.nav.SettingsNav
import javax.inject.Inject

class NavigationManagerImpl @Inject constructor() : NavigationManager {

    private val lastDestinations = mutableMapOf<String, String>()

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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            appFeatures.forEach { feature ->
                if (destination.route?.startsWith(feature.navGraph.route) == true) {
                    lastDestinations[feature.navGraph.route] = destination.route ?: feature.navGraph.startDestination
                }
            }
        }
    }

    override fun navigateToSettings(navController: NavController) {
        navigateToFeature(navController, SettingsNav)
    }

    override fun navigateToIncome(navController: NavController) {
        navigateToFeature(navController, IncomeNav)
    }

    override fun navigateToAccount(navController: NavController) {
        navigateToFeature(navController, AccountNav)
    }

    override fun navigateToCategories(navController: NavController) {
        navigateToFeature(navController, CategoriesNav)
    }

    override fun navigateToExpenses(navController: NavController) {
        navigateToFeature(navController, ExpensesNav)
    }

    private fun navigateToFeature(navController: NavController, feature: FeatureNav) {
        val currentRoute = navController.currentDestination?.route

        if (currentRoute?.startsWith(feature.navGraph.route) == true) {
            return
        }

        val lastDestination = lastDestinations[feature.navGraph.route] ?: feature.navGraph.startDestination

        if (lastDestination != feature.navGraph.route) {
            navController.navigate(lastDestination) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        } else {
            feature.navigate(navController)
        }
    }

    override fun navigateBack(navController: NavController) {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        }
    }
}