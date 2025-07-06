package ru.fav.moneytrace.account.impl.ui.nav

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.fav.moneytrace.account.impl.ui.main.AccountScreen
import ru.fav.moneytrace.account.impl.ui.update.AccountUpdateScreen
import ru.fav.moneytrace.account.impl.ui.update.state.AccountUpdateEvent
import ru.fav.moneytrace.navigation.FeatureNav
import ru.fav.moneytrace.navigation.NavigationManager

object AccountNav: FeatureNav {
    override val navGraph = AccountNavGraph()

    override val name: String = "AccountFeature"

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
                AccountScreen(
                    onUpdateClick = {
                        navigateToAccountUpdate(navController)
                    })
            }
            composable(route = navGraph.updateDestination) {
                AccountUpdateScreen(
                    onCloseClick = {
                        navigationManager.navigateBack(navController)
                    },
                    onSaveClick = {
                        navigationManager.navigateToAccount(navController)
                    }
                )
            }
        }
    }

    override fun navigate(navController: NavController, args: Bundle?) {
        navController.navigate(navGraph.route)
    }

    private fun navigateToAccountUpdate(navController: NavController) {
        navController.navigate(navGraph.updateDestination)
    }
}