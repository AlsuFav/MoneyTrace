package ru.fav.moneytrace.navigation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.fav.moneytrace.account.impl.ui.nav.AccountNav
import ru.fav.moneytrace.navigation.NavigationManager

@Composable
fun AppNavGraph(
    navigationManager: NavigationManager,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AccountNav.navGraph.route,
        modifier = modifier
    ) {
        navigationManager.buildNavGraph(this, navController)
    }
}