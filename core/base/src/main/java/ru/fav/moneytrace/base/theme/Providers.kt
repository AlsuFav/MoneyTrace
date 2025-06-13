package ru.fav.moneytrace.base.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

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