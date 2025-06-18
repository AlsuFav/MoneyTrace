package ru.fav.moneytrace.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import ru.fav.moneytrace.ui.theme.Providers

@Composable
fun MTText(
    text: String,
    style: TextStyle = Providers.typography.bodyL,
    color: Color = Providers.color.onSurface,
    maxLines: Int = Int.MAX_VALUE,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = Providers.spacing.none,
        vertical = Providers.spacing.l
    )
) {
    Text(
        text = text,
        style = style,
        modifier = modifier.padding(contentPadding),
        color = color,
        maxLines = maxLines
    )
}