package ru.fav.moneytrace.ui.theme.designsystem

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val none: Dp = 0.dp,
    val xxs: Dp = 2.dp,
    val xs: Dp = 4.dp,
    val s: Dp = 8.dp,
    val m: Dp = 16.dp,
    val l: Dp = 22.dp,
    val xl: Dp = 30.dp,
    val xxl: Dp = 40.dp,
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }