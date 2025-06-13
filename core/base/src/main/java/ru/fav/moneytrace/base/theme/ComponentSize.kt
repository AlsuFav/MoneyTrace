package ru.fav.moneytrace.base.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ComponentSize(
    val iconSmall: Dp = 16.dp,
    val iconMedium: Dp = 24.dp,
    val iconLarge: Dp = 32.dp,
    val buttonSmall: Dp = 40.dp,
    val buttonMedium: Dp = 48.dp,
    val buttonLarge: Dp = 56.dp,
    val textFieldMedium: Dp = 60.dp
)

val LocalComponentSizes = staticCompositionLocalOf { ComponentSize() }