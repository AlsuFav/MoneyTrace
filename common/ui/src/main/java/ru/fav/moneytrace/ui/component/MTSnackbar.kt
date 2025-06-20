package ru.fav.moneytrace.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.theme.Providers

@Composable
fun MTSnackbar(
    title: String = stringResource(R.string.error),
    message: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Providers.color.inverseSurface,
    contentColor: Color = Providers.color.inverseOnSurface,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = Providers.spacing.m,
        vertical = Providers.spacing.m
    ),
    shape: Shape = Providers.shape.l
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
        contentColor = contentColor,
        shape = shape,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.padding(end = Providers.spacing.m),
                contentAlignment = Alignment.Center
            ) {
                MTIcon(
                    painter = painterResource(R.drawable.ic_info),
                    contentDescription = null,
                    tint = contentColor
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                MTText(
                    text = title,
                    style = Providers.typography.bodyL,
                    color = contentColor,
                    contentPadding = PaddingValues(Providers.spacing.xs)
                )

                MTText(
                    text = message,
                    style = Providers.typography.bodyM,
                    color = contentColor.copy(alpha = 0.8f),
                    contentPadding = PaddingValues(Providers.spacing.xs)
                )
            }
        }
    }
}

@Composable
fun MTSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    snackbar: @Composable (SnackbarData) -> Unit = { data ->
        MTSnackbar(
            message = data.visuals.message
        )
    }
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier,
        snackbar = snackbar
    )
}