package ru.fav.moneytrace.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.fav.moneytrace.ui.theme.Providers

/**
 * Кастомная верхняя панель приложения с центрированным заголовком.
 *
 * @param title Заголовок панели
 * @param navigationIcon Иконка навигации (обычно кнопка "Назад")
 * @param actions Действия в правой части панели
 * @param backgroundColor Цвет фона панели
 * @param foregroundColor Цвет текста и иконок
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MTCenterAlignedTopAppBar(
    title: String,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = Providers.color.primary,
    foregroundColor: Color = Providers.color.secondary,
) {

    CenterAlignedTopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = foregroundColor,
            actionIconContentColor = foregroundColor,
            navigationIconContentColor = foregroundColor
        ),
        navigationIcon = { navigationIcon?.invoke() },
        actions = actions,
    )
}