package ru.fav.moneytrace.account.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.fav.moneytrace.base.state.TopAppBarState
import ru.fav.moneytrace.basefeature.FeatureBase
import ru.fav.moneytrace.navigation.NavGraph
import ru.fav.moneytrace.navigation.NavigationManager

object AccountFeature: FeatureBase {
    override val navGraph = object : NavGraph() {
        override val route = "account"
        override val startDestination = "account_main_screen"
    }

    override val name: String = "AccountFeature"

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
                AccountScreen(topAppBarSetter)
            }
        }
    }

    override fun navigate(navController: NavController, args: Bundle?) {
        navController.navigate(navGraph.route)
    }
}