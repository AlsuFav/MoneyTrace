package ru.fav.moneytrace.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import ru.fav.moneytrace.ui.theme.designsystem.ComponentSize
import ru.fav.moneytrace.ui.theme.designsystem.DarkColorScheme
import ru.fav.moneytrace.ui.theme.designsystem.LightColorScheme
import ru.fav.moneytrace.ui.theme.designsystem.LocalColorScheme
import ru.fav.moneytrace.ui.theme.designsystem.LocalComponentSizes
import ru.fav.moneytrace.ui.theme.designsystem.LocalShape
import ru.fav.moneytrace.ui.theme.designsystem.LocalSpacing
import ru.fav.moneytrace.ui.theme.designsystem.LocalTypography
import ru.fav.moneytrace.ui.theme.designsystem.Shape
import ru.fav.moneytrace.ui.theme.designsystem.Spacing
import ru.fav.moneytrace.ui.theme.designsystem.Typography

/**
 * Основная тема приложения, предоставляющая цветовую схему и дизайн-систему.
 *
 * @param darkTheme Использовать ли темную тему
 * @param content Содержимое, к которому применяется тема
 */

@Composable
fun MTTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(
        LocalColorScheme provides colors,
        LocalTypography provides Typography(),
        LocalSpacing provides Spacing(),
        LocalShape provides Shape(),
        LocalComponentSizes provides ComponentSize()
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = MaterialTheme.typography,
            content = content
        )
    }
}