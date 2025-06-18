package ru.fav.moneytrace.account.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.fav.moneytrace.account.R
import ru.fav.moneytrace.ui.component.MTCenterAlignedTopAppBar
import ru.fav.moneytrace.ui.component.MTEmojiIcon
import ru.fav.moneytrace.ui.component.MTFloatingActionButton
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTIconButton
import ru.fav.moneytrace.ui.component.MTListItem
import ru.fav.moneytrace.util.extension.toCurrencySymbol
import ru.fav.moneytrace.ui.theme.Providers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            MTCenterAlignedTopAppBar(
                title = stringResource(R.string.my_account),
                actions = {
                    MTIconButton(
                        onClick = { }
                    ) {
                        MTIcon(
                            painter = painterResource(ru.fav.moneytrace.ui.R.drawable.ic_edit),
                            contentDescription = stringResource(ru.fav.moneytrace.ui.R.string.edit),
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            MTFloatingActionButton(
                onClick = { },
                contentDescription = stringResource(ru.fav.moneytrace.ui.R.string.add),
                painter = painterResource(ru.fav.moneytrace.ui.R.drawable.ic_add),
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                            painter = painterResource(ru.fav.moneytrace.ui.R.drawable.ic_more),
                            contentDescription = stringResource(ru.fav.moneytrace.ui.R.string.more),
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
                            painter = painterResource(ru.fav.moneytrace.ui.R.drawable.ic_more),
                            contentDescription = stringResource(ru.fav.moneytrace.ui.R.string.more),
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
        }
    }
}