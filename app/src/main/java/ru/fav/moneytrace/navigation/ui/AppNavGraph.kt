package ru.fav.moneytrace.navigation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.fav.moneytrace.account.impl.ui.nav.AccountNav
import ru.fav.moneytrace.navigation.NavigationManager

/**
 * Корневой навигационный граф приложения.
 *
 * Создает NavHost с настроенным навигационным контроллером и делегирует
 * построение графа навигации менеджеру навигации. Устанавливает начальный
 * пункт назначения на маршрут аккаунта.
 *
 * @param navigationManager Менеджер навигации для построения графа
 * @param modifier Модификатор для настройки внешнего вида и поведения
 * @param navController Контроллер навигации, по умолчанию создается новый
 */

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