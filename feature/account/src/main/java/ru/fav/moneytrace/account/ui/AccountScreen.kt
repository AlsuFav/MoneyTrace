package ru.fav.moneytrace.account.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.fav.moneytrace.account.R
import ru.fav.moneytrace.base.component.MTEmojiIcon
import ru.fav.moneytrace.base.component.MTFloatingActionButton
import ru.fav.moneytrace.base.component.MTIcon
import ru.fav.moneytrace.base.component.MTIconButton
import ru.fav.moneytrace.base.component.MTListItem
import ru.fav.moneytrace.base.extension.toCurrencySymbol
import ru.fav.moneytrace.base.state.TopAppBarState
import ru.fav.moneytrace.base.theme.Providers

@Composable
fun AccountScreen(
    topAppBarSetter: (TopAppBarState) -> Unit,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val topAppBarState = TopAppBarState(
        isVisible = true,
        title = stringResource(R.string.my_account),
        actions = {
            MTIconButton(
                onClick = { }
            ) {
                MTIcon(
                    painter = painterResource(ru.fav.moneytrace.base.R.drawable.ic_edit),
                    contentDescription = stringResource(ru.fav.moneytrace.base.R.string.edit),
                )
            }
        }
    )

    LaunchedEffect(topAppBarState) {
        topAppBarSetter(topAppBarState)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            MTListItem(
                leadingIcon = {
                    MTEmojiIcon(
                        emoji = "\uD83D\uDCB0",
                        backgroundColor = Color.White
                    )
                },
                title = stringResource(R.string.balance),
                trailingTitle = "${state.account.balance} ${state.account.currency.toCurrencySymbol()}",
                trailingIcon = {
                    MTIcon(
                        painter = painterResource(ru.fav.moneytrace.base.R.drawable.ic_more),
                        contentDescription = stringResource(ru.fav.moneytrace.base.R.string.more),
                    )
                },
                onClick = { },
                textPadding = PaddingValues(
                    horizontal = Providers.spacing.none,
                    vertical = Providers.spacing.m
                ),
                backgroundColor = Providers.color.secondaryContainer
            )

            HorizontalDivider()

            MTListItem(
                title = stringResource(R.string.valute),
                trailingTitle = state.account.currency.toCurrencySymbol(),
                trailingIcon = {
                    MTIcon(
                        painter = painterResource(ru.fav.moneytrace.base.R.drawable.ic_more),
                        contentDescription = stringResource(ru.fav.moneytrace.base.R.string.more),
                    )
                },
                onClick = { },
                textPadding = PaddingValues(
                    horizontal = Providers.spacing.none,
                    vertical = Providers.spacing.m
                ),
                backgroundColor = Providers.color.secondaryContainer
            )
        }

        MTFloatingActionButton(
            onClick = {
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(Providers.spacing.m),
            contentDescription = stringResource(ru.fav.moneytrace.base.R.string.add),
            painter = painterResource(ru.fav.moneytrace.base.R.drawable.ic_add),
        )
    }
}