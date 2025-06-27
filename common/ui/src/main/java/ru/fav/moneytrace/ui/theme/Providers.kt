package ru.fav.moneytrace.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import ru.fav.moneytrace.ui.theme.designsystem.ComponentSize
import ru.fav.moneytrace.ui.theme.designsystem.LocalColorScheme
import ru.fav.moneytrace.ui.theme.designsystem.LocalComponentSizes
import ru.fav.moneytrace.ui.theme.designsystem.LocalShape
import ru.fav.moneytrace.ui.theme.designsystem.LocalSpacing
import ru.fav.moneytrace.ui.theme.designsystem.LocalTypography
import ru.fav.moneytrace.ui.theme.designsystem.Shape
import ru.fav.moneytrace.ui.theme.designsystem.Spacing
import ru.fav.moneytrace.ui.theme.designsystem.Typography

/**
 * Провайдер для доступа к элементам дизайн-системы приложения.
 *
 * Предоставляет единую точку доступа к отступам, формам, типографике,
 * цветам и размерам компонентов через Composition Local.
 */

object Providers {
    val spacing: Spacing
        @Composable
        get() = LocalSpacing.current

    val shape: Shape
        @Composable
        get() = LocalShape.current

    val typography: Typography
        @Composable
        get() = LocalTypography.current

    val color: ColorScheme
        @Composable
        get() = LocalColorScheme.current

    val componentSize: ComponentSize
        @Composable
        get() = LocalComponentSizes.current
}