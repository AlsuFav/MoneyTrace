package ru.fav.moneytrace.base.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import ru.fav.moneytrace.base.theme.Providers

@Composable
fun MTIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = Providers.componentSize.buttonMedium,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(size),
        enabled = enabled
    ) {
        content()
    }
}