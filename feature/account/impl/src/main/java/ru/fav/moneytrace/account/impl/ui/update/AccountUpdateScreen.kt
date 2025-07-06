package ru.fav.moneytrace.account.impl.ui.update

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.fav.moneytrace.account.impl.ui.update.state.AccountUpdateEvent
import ru.fav.moneytrace.account.impl.ui.update.component.AccountUpdateItem
import ru.fav.moneytrace.account.impl.ui.update.component.AccountUpdateShimmerItem
import ru.fav.moneytrace.account.impl.ui.update.component.CurrencySelectionBottomSheet
import ru.fav.moneytrace.account.impl.ui.update.state.AccountUpdateEffect
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTCenterAlignedTopAppBar
import ru.fav.moneytrace.ui.component.MTErrorDialog
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountUpdateScreen(
    onCloseClick: () -> Unit,
    onSaveClick: () -> Unit,
    viewModel: AccountUpdateViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.reduce(AccountUpdateEvent.LoadData)
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AccountUpdateEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                is AccountUpdateEffect.AccountUpdated -> onSaveClick()
            }
        }
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
            title = stringResource(ru.fav.moneytrace.account.impl.R.string.my_account),
            navigationIcon = {
                MTIconButton(
                    onClick = onCloseClick
                ) {
                    MTIcon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = stringResource(R.string.exit),
                    )
                }
            },
            actions = {
                MTIconButton(
                    onClick = { viewModel.reduce(AccountUpdateEvent.OnDoneClicked) }
                ) {
                    MTIcon(
                        painter = painterResource(R.drawable.ic_done),
                        contentDescription = stringResource(R.string.done),
                    )
                }
            }
        )

        Box(
            modifier = Modifier.weight(1f)
        ) {
            when {
                state.isLoading -> {
                    AccountUpdateShimmerItem()
                }
                else -> {
                    AccountUpdateItem(
                        account = state.account,
                        onCurrencyClick = {
                            viewModel.reduce(AccountUpdateEvent.ShowCurrencyBottomSheet)
                        }
                    )
                }
            }
        }
    }

    if (state.showBottomSheet) {
        CurrencySelectionBottomSheet(
            currencies = state.currencies,
            onCurrencySelected = { currency ->
                viewModel.reduce(AccountUpdateEvent.SelectCurrency(currency))
                viewModel.reduce(AccountUpdateEvent.HideCurrencyBottomSheet)
            },
            onDismiss = {
                viewModel.reduce(AccountUpdateEvent.HideCurrencyBottomSheet)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }

    state.showErrorDialog?.let { message ->
        MTErrorDialog(
            message = message,
            confirmButtonText = stringResource(R.string.repeat),
            dismissButtonText = stringResource(R.string.exit),
            onConfirm = {
                viewModel.reduce(AccountUpdateEvent.LoadData)
            },
            onDismiss = {
                viewModel.reduce(AccountUpdateEvent.HideErrorDialog)
            }
        )
    }
}