package ru.fav.moneytrace.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.fav.moneytrace.base.component.MTIcon
import ru.fav.moneytrace.base.component.MTListItem
import ru.fav.moneytrace.base.state.TopAppBarState
import ru.fav.moneytrace.base.theme.Providers
import ru.fav.moneytrace.settings.R
import androidx.compose.runtime.getValue

@Composable
fun SettingsScreen(topAppBarSetter: (TopAppBarState) -> Unit) {
    val topAppBarState = TopAppBarState(
        isVisible = true,
        title = stringResource(R.string.settings),
    )

    LaunchedEffect(topAppBarState) {
        topAppBarSetter(topAppBarState)
    }

    val moreIcon: @Composable () -> Unit = {
        MTIcon(
            painter = painterResource(ru.fav.moneytrace.base.R.drawable.ic_details),
            contentDescription = stringResource(ru.fav.moneytrace.base.R.string.details),
        )
    }

    var isDarkMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MTListItem(
            title = stringResource(R.string.dark_theme),
            trailingIcon = {
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { isDarkMode = it }
                )
            },
            onClick = {
                isDarkMode = !isDarkMode
            },
            textPadding = PaddingValues(
                horizontal = Providers.spacing.none,
                vertical = Providers.spacing.m
            )
        )

        HorizontalDivider()

        MTListItem(
            title = stringResource(R.string.main_color),
            trailingIcon = moreIcon,
            onClick = {  },
            textPadding = PaddingValues(
                horizontal = Providers.spacing.none,
                vertical = Providers.spacing.m
            )
        )

        HorizontalDivider()

        MTListItem(
            title = stringResource(R.string.sounds),
            trailingIcon = moreIcon,
            onClick = {  },
            textPadding = PaddingValues(
                horizontal = Providers.spacing.none,
                vertical = Providers.spacing.m
            )
        )

        HorizontalDivider()

        MTListItem(
            title = stringResource(R.string.haptics),
            trailingIcon = moreIcon,
            onClick = { /* Navigate to haptics settings */ },
            textPadding = PaddingValues(
                horizontal = Providers.spacing.none,
                vertical = Providers.spacing.m
            )
        )

        HorizontalDivider(color = Providers.color.outline.copy(alpha = 0.2f))

        MTListItem(
            title = stringResource(R.string.password),
            trailingIcon = moreIcon,
            onClick = { /* Navigate to password settings */ },
            textPadding = PaddingValues(
                horizontal = Providers.spacing.none,
                vertical = Providers.spacing.m
            )
        )

        HorizontalDivider()

        MTListItem(
            title = stringResource(R.string.synchronization),
            trailingIcon = moreIcon,
            onClick = { /* Navigate to sync settings */ },
            textPadding = PaddingValues(
                horizontal = Providers.spacing.none,
                vertical = Providers.spacing.m
            )
        )

        HorizontalDivider()

        MTListItem(
            title = stringResource(R.string.language),
            trailingIcon = moreIcon,
            onClick = { /* Navigate to language settings */ },
            textPadding = PaddingValues(
                horizontal = Providers.spacing.none,
                vertical = Providers.spacing.m
            )
        )

        HorizontalDivider()

        MTListItem(
            title = stringResource(R.string.about_program),
            trailingIcon = moreIcon,
            onClick = { /* Navigate to about screen */ },
            textPadding = PaddingValues(
                horizontal = Providers.spacing.none,
                vertical = Providers.spacing.m
            )
        )

        HorizontalDivider()
    }
}