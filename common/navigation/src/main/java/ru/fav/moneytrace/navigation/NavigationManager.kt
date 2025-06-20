package ru.fav.moneytrace.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface NavigationManager {
    fun buildNavGraph(
        builder: NavGraphBuilder,
        navController: NavController,
    )

    fun navigateToSettings(navController: NavController)
    fun navigateToIncome(navController: NavController)
    fun navigateToExpenses(navController: NavController)
    fun navigateToCategories(navController: NavController)
    fun navigateToAccount(navController: NavController)

    fun navigateBack(navController: NavController)
}