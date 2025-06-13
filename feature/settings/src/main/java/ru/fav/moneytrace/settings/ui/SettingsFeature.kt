package ru.fav.moneytrace.settings.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.fav.moneytrace.base.state.TopAppBarState
import ru.fav.moneytrace.basefeature.FeatureBase
import ru.fav.moneytrace.navigation.NavGraph
import ru.fav.moneytrace.navigation.NavigationManager

object SettingsFeature: FeatureBase {
    override val navGraph = object : NavGraph() {
        override val route = "settings"
        override val startDestination = "settings_main_screen"
    }
    override val name: String = "SettingsFeature"

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
                SettingsScreen(topAppBarSetter)
            }
        }
    }

    override fun navigate(navController: NavController, args: Bundle?) {
        navController.navigate(navGraph.route)
    }
}