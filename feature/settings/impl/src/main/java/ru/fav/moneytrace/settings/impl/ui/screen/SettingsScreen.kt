package ru.fav.moneytrace.settings.impl.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTListItem
import ru.fav.moneytrace.ui.theme.Providers
import androidx.compose.runtime.getValue
import ru.fav.moneytrace.settings.impl.R
import ru.fav.moneytrace.ui.component.MTCenterAlignedTopAppBar

/**
 * Composable функция для отображения экрана настроек приложения.
 *
 * Экран содержит верхнюю панель с заголовком и список настраиваемых параметров приложения,
 * включая темную тему, основной цвет, звуки, тактильную обратную связь, пароль,
 * синхронизацию, язык и информацию о программе. Каждый элемент списка представлен
 * в виде кликабельного элемента с соответствующим заголовком и иконкой.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val moreIcon: @Composable () -> Unit = {
        MTIcon(
            painter = painterResource(ru.fav.moneytrace.ui.R.drawable.ic_details),
            contentDescription = stringResource(ru.fav.moneytrace.ui.R.string.details),
        )
    }

    var isDarkMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MTCenterAlignedTopAppBar(
            title = stringResource(R.string.settings)
        )

        Column(
            modifier = Modifier.fillMaxSize()
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
                height = Providers.spacing.xxl,
            )

            HorizontalDivider()

            MTListItem(
                title = stringResource(R.string.main_color),
                trailingIcon = moreIcon,
                onClick = {  },
                height = Providers.spacing.xxl,
            )

            HorizontalDivider()

            MTListItem(
                title = stringResource(R.string.sounds),
                trailingIcon = moreIcon,
                onClick = {  },
                height = Providers.spacing.xxl,
            )

            HorizontalDivider()

            MTListItem(
                title = stringResource(R.string.haptics),
                trailingIcon = moreIcon,
                onClick = { /* Navigate to haptics settings */ },
                height = Providers.spacing.xxl,
            )

            HorizontalDivider(color = Providers.color.outline.copy(alpha = 0.2f))

            MTListItem(
                title = stringResource(R.string.password),
                trailingIcon = moreIcon,
                onClick = { /* Navigate to password settings */ },
                height = Providers.spacing.xxl,
            )

            HorizontalDivider()

            MTListItem(
                title = stringResource(R.string.synchronization),
                trailingIcon = moreIcon,
                onClick = { /* Navigate to sync settings */ },
                height = Providers.spacing.xxl,
            )

            HorizontalDivider()

            MTListItem(
                title = stringResource(R.string.language),
                trailingIcon = moreIcon,
                onClick = { /* Navigate to language settings */ },
                height = Providers.spacing.xxl,
            )

            HorizontalDivider()

            MTListItem(
                title = stringResource(R.string.about_program),
                trailingIcon = moreIcon,
                onClick = { /* Navigate to about screen */ },
                height = Providers.spacing.xxl,
            )

            HorizontalDivider()
        }
    }
}