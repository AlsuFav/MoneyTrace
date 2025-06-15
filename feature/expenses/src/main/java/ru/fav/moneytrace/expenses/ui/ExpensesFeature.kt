package ru.fav.moneytrace.expenses.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.fav.moneytrace.base.state.TopAppBarState
import ru.fav.moneytrace.basefeature.FeatureBase
import ru.fav.moneytrace.navigation.NavGraph
import ru.fav.moneytrace.navigation.NavigationManager

object ExpensesFeature: FeatureBase {
    override val navGraph = object : NavGraph() {
        override val route = "expenses"
        override val startDestination = "expenses_main_screen"
    }
    override val name: String = "ExpensesFeature"

    override fun routes(
        builder: NavGraphBuilder,
        navController: NavController,
        navigationManager: NavigationManager,
        topAppBarSetter: (TopAppBarState) -> Unit
    ) {
        builder.navigation(
            startDestination = navGraph.startDestination,
            route = navGraph.route
        ) {
            composable(route = navGraph.startDestination) {
                ExpensesScreen(topAppBarSetter)
            }
        }
    }

    override fun navigate(navController: NavController, args: Bundle?) {
        navController.navigate(navGraph.route)
    }
}