package ru.fav.moneytrace.ui.theme.designsystem

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

data class Shape(
    val xs: CornerBasedShape = RoundedCornerShape(4.dp),
    val s: CornerBasedShape = RoundedCornerShape(8.dp),
    val m: CornerBasedShape = RoundedCornerShape(12.dp),
    val l: CornerBasedShape = RoundedCornerShape(16.dp),
    val xl: CornerBasedShape = RoundedCornerShape(24.dp),
)

val LocalShape = staticCompositionLocalOf { Shape() }