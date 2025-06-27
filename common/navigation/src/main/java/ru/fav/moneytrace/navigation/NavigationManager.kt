package ru.fav.moneytrace.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

/**
 * Интерфейс для управления навигацией между фичами приложения.
 *
 * Централизует логику навигации и обеспечивает слабую связность между модулями,
 * позволяя фичам навигировать друг к другу без прямых зависимостей.
 */

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