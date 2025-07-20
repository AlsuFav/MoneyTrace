package ru.fav.moneytrace.analysis.impl.ui.nav

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.fav.moneytrace.analysis.impl.ui.screen.AnalysisScreen
import ru.fav.moneytrace.navigation.FeatureNav
import ru.fav.moneytrace.navigation.NavigationManager
import ru.fav.moneytrace.transaction.api.model.TransactionType

object AnalysisNav: FeatureNav {
    override val navGraph = AnalysisNavGraph()
    override val name: String = "AnalysisFeature"

    override fun routes(
        builder: NavGraphBuilder,
        navController: NavController,
        navigationManager: NavigationManager,
    ) {
        builder.navigation(
            startDestination = navGraph.startDestination,
            route = navGraph.route
        ) {
            composable(
                route = navGraph.startDestination,
                arguments = listOf(
                    navArgument("transactionType") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val transactionTypeString = backStackEntry.arguments?.getString("transactionType") ?: ""
                val transactionType = TransactionType.fromString(transactionTypeString)

                AnalysisScreen(
                    transactionType = transactionType,
                    onBackClick = {
                        navigationManager.navigateBack(navController)
                    },
                    onCategoryClick = {}
                )
            }
        }
    }

    override fun navigate(navController: NavController, args: Bundle?) {
        navController.navigate(navGraph.route)
    }

    fun navigateToAnalysis(navController: NavController, transactionType: TransactionType) {
        navController.navigate(navGraph.mainRoute(transactionType))
    }
}