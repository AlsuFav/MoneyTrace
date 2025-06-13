package ru.fav.moneytrace.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ru.fav.moneytrace.base.state.TopAppBarState

interface NavigationManager {
    fun buildNavGraph(
        builder: NavGraphBuilder,
        navController: NavController,
        onTopAppBarStateChange: (TopAppBarState) -> Unit
    )

    fun navigateToSettings(navController: NavController)
    fun navigateToIncome(navController: NavController)
    fun navigateToExpenses(navController: NavController)
    fun navigateToStats(navController: NavController)
    fun navigateToAccount(navController: NavController)
}