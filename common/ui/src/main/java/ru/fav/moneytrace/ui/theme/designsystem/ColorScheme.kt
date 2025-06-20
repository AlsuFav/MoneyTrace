package ru.fav.moneytrace.ui.theme.designsystem

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2AE881),
    secondary = Color(0xFF1D1B20),
    inverseSurface = Color(0xFF8C8C8C),
    inverseOnSurface = Color(0xFFFFFFEF),
    onPrimary = Color(0xFFFFFFEF),
    secondaryContainer = Color(0xFFD4FAE6),
    onSecondaryContainer = Color(0xFF2AE881),
    surface = Color(0xFFFEF7FF),
    surfaceContainer = Color(0xFFF3EDF7),
    onSurface = Color(0xFF1D1B20),
    onSurfaceVariant = Color(0xFF49454F),
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF2AE881),
    secondary = Color(0xFF1D1B20),
    onPrimary = Color(0xFFFFFFEF),
    secondaryContainer = Color(0xFFD4FAE6),
    onSecondaryContainer = Color(0xFF2AE881),
    surface = Color(0xFF2AE881),
    surfaceContainer = Color(0xFFF3EDF7),
    onSurface = Color(0xFF1D1B20),
    onSurfaceVariant = Color(0xFF49454F),
)

val LocalColorScheme = staticCompositionLocalOf { lightColorScheme() }
