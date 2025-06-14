package ru.fav.moneytrace.base.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import ru.fav.moneytrace.base.theme.Providers

@Composable
fun MTFloatingActionButton(
    onClick: () -> Unit,
    contentDescription: String,
    painter: Painter,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Providers.color.primary,
    iconTint: Color = Providers.color.onPrimary,
    size: Dp = Providers.componentSize.buttonLarge,
    iconSize: Dp = Providers.componentSize.iconMedium,
) {
    Box(
        modifier = modifier
            .size(size)
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        MTIcon(
            painter = painter,
            contentDescription = contentDescription,
            tint = iconTint,
            modifier = Modifier.size(iconSize)
        )
    }
}