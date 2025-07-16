package ru.fav.moneytrace.income.impl.ui.nav

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.fav.moneytrace.income.impl.ui.screen.history.IncomeHistoryScreen
import ru.fav.moneytrace.income.impl.ui.screen.today.IncomeTodayScreen
import ru.fav.moneytrace.navigation.FeatureNav
import ru.fav.moneytrace.navigation.NavGraph
import ru.fav.moneytrace.navigation.NavigationManager

object
IncomeNav: FeatureNav {
    override val navGraph = IncomeNavGraph()
    override val name: String = "IncomeFeature"

    override fun routes(
        builder: NavGraphBuilder,
        navController: NavController,
        navigationManager: NavigationManager,
    ) {
        builder.navigation(
            startDestination = navGraph.startDestination,
            route = navGraph.route
        ) {
            composable(route = navGraph.startDestination) {
                IncomeTodayScreen(
                    onHistoryClick = {
                        navigateToHistory(navController)
                    },
                    onIncomeClick = { id ->
                        navigationManager.navigateToTransactionUpdate(navController, id)
                    },
                    onAddClick = { navigationManager.navigateToTransactionCreate(navController) },
                )
            }
            composable(route = navGraph.historyDestination) {
                IncomeHistoryScreen(
                    onBackClick = {
                        navigate(navController)
                    },
                    onIncomeClick = { id ->
                        navigationManager.navigateToTransactionUpdate(navController, id)
                    },
                )
            }
        }
    }

    override fun navigate(navController: NavController, args: Bundle?) {
        navController.navigate(navGraph.route)
    }

    private fun navigateToHistory(navController: NavController) {
        navController.navigate(navGraph.historyDestination)
    }
}