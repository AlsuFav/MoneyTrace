package ru.fav.moneytrace.account.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.fav.moneytrace.account.R
import ru.fav.moneytrace.account.ui.component.AccountItem
import ru.fav.moneytrace.account.ui.component.AccountShimmerItem
import ru.fav.moneytrace.account.ui.state.AccountEvent
import ru.fav.moneytrace.ui.component.MTCenterAlignedTopAppBar
import ru.fav.moneytrace.ui.component.MTErrorDialog
import ru.fav.moneytrace.ui.component.MTFloatingActionButton
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTIconButton
import ru.fav.moneytrace.ui.theme.Providers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.reduce(AccountEvent.LoadAccount)
    }

    DisposableEffect(viewModel) {
        onDispose {
            viewModel.cancelAllTasks()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
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

        Box(
            modifier = Modifier.weight(1f)
        ) {
            when {
                state.isLoading -> {
                    AccountShimmerItem(
                    )
                }

                else -> {
                    AccountItem(
                        account = state.account,
                    )
                }
            }

            MTFloatingActionButton(
                onClick = { },
                contentDescription = stringResource(ru.fav.moneytrace.ui.R.string.add),
                painter = painterResource(ru.fav.moneytrace.ui.R.drawable.ic_add),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(Providers.spacing.m)
            )
        }
    }
    state.showErrorDialog?.let { message ->
        MTErrorDialog(
            message = message,
            confirmButtonText = stringResource(ru.fav.moneytrace.ui.R.string.repeat),
            dismissButtonText = stringResource(ru.fav.moneytrace.ui.R.string.exit),
            onConfirm = {
                viewModel.reduce(AccountEvent.LoadAccount)
            },
            onDismiss = {
                viewModel.reduce(AccountEvent.HideErrorDialog)
            }
        )
    }
}