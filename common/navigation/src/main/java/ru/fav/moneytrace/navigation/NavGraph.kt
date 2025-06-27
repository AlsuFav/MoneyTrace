package ru.fav.moneytrace.navigation

/**
 * Базовый класс для определения графа навигации фичи.
 *
 * Содержит основные параметры навигационного графа: корневой маршрут
 * и стартовый экран внутри графа.
 */

abstract class NavGraph {
    abstract val route: String
    abstract val startDestination: String
}