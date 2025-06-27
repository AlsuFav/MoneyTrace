package ru.fav.moneytrace.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

/**
 * Интерфейс для навигационных компонентов фич в многомодульной архитектуре.
 *
 * Определяет контракт для регистрации маршрутов и навигации внутри отдельных фич,
 * обеспечивая слабую связность между модулями.
 */

interface FeatureNav {
    val navGraph: NavGraph

    val name: String

    fun routes(builder: NavGraphBuilder,
               navController: NavController,
               navigationManager: NavigationManager,
    )

    fun navigate(navController: NavController, args: Bundle? = null)
}