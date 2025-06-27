package ru.fav.moneytrace

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.fav.moneytrace.navigation.NavigationManager
import ru.fav.moneytrace.navigation.ui.AppNavGraph
import ru.fav.moneytrace.navigation.ui.BottomNavigationBar
import ru.fav.moneytrace.navigation.ui.rememberBottomNavItems
import ru.fav.moneytrace.ui.theme.Providers

/**
 * Главный экран приложения с нижней навигацией.
 *
 * Содержит граф навигации и панель нижней навигации для переключения между основными разделами.
 *
 * @param navigationManager Менеджер навигации для управления переходами
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navigationManager: NavigationManager) {
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route
    val bottomNavItems = rememberBottomNavItems(navController, navigationManager)


    Column(
        Modifier.background(Providers.color.surface)
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            AppNavGraph(
                navigationManager = navigationManager,
                navController = navController
            )
        }

        BottomNavigationBar(
            bottomNavItems = bottomNavItems,
            currentRoute = currentRoute
        )
    }
}