package ru.fav.moneytrace.categories.impl.ui.nav

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.fav.moneytrace.categories.impl.ui.CategoriesScreen
import ru.fav.moneytrace.navigation.FeatureNav
import ru.fav.moneytrace.navigation.NavGraph
import ru.fav.moneytrace.navigation.NavigationManager

object CategoriesNav: FeatureNav {
    override val navGraph = object : NavGraph() {
        override val route = "categories"
        override val startDestination = "categories_main_screen"
    }
    override val name: String = "CategoriesFeature"

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
                CategoriesScreen()
            }
        }
    }

    override fun navigate(navController: NavController, args: Bundle?) {
        navController.navigate(navGraph.route)
    }
}