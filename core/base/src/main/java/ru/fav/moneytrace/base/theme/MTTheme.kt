package ru.fav.moneytrace.base.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider


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