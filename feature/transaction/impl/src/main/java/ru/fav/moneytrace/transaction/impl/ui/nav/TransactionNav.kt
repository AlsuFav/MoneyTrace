package ru.fav.moneytrace.transaction.impl.ui.nav

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.fav.moneytrace.navigation.FeatureNav
import ru.fav.moneytrace.navigation.NavigationManager
import ru.fav.moneytrace.transaction.api.model.TransactionType
import ru.fav.moneytrace.transaction.impl.ui.screen.create.TransactionCreateScreen
import ru.fav.moneytrace.transaction.impl.ui.screen.update.TransactionUpdateScreen

object TransactionNav: FeatureNav {
    override val navGraph = TransactionNavGraph()
    override val name: String = "TransactionFeature"

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
                route = navGraph.updateDestination,
                arguments = listOf(
                    navArgument("transactionId") { type = NavType.IntType },
                    navArgument("transactionType") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val transactionId = backStackEntry.arguments?.getInt("transactionId") ?: 0
                val transactionTypeString = backStackEntry.arguments?.getString("transactionType") ?: ""
                val transactionType = TransactionType.fromString(transactionTypeString)

                TransactionUpdateScreen(
                    transactionId = transactionId,
                    transactionType = transactionType,
                    onCloseClick = {
                        navigationManager.navigateBack(navController)
                    },
                    onSaveClick = {
                        navigationManager.navigateBack(navController)
                    }
                )
            }

            composable(
                route = navGraph.createDestination,
                arguments = listOf(
                    navArgument("transactionType") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val transactionTypeString = backStackEntry.arguments?.getString("transactionType") ?: ""
                val transactionType = TransactionType.fromString(transactionTypeString)

                TransactionCreateScreen(
                    transactionType = transactionType,
                    onCloseClick = {
                        navigationManager.navigateBack(navController)
                    },
                    onSaveClick = {
                        navigationManager.navigateBack(navController)
                    }
                )
            }
        }
    }

    override fun navigate(navController: NavController, args: Bundle?) {
        navController.navigate(navGraph.route)
    }

    fun navigateToCreate(navController: NavController, transactionType: TransactionType) {
        navController.navigate(navGraph.createRoute(transactionType))
    }

    fun navigateToUpdate(navController: NavController, transactionId: Int, transactionType: TransactionType) {
        navController.navigate(navGraph.updateRoute(transactionId, transactionType))
    }
}