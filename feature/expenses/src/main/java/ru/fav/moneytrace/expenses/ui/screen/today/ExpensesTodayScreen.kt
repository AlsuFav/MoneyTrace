package ru.fav.moneytrace.expenses.ui.screen.today

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.fav.moneytrace.ui.component.MTFloatingActionButton
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTIconButton
import ru.fav.moneytrace.ui.theme.Providers
import ru.fav.moneytrace.expenses.R
import ru.fav.moneytrace.expenses.ui.screen.today.component.ExpensesTodayList
import ru.fav.moneytrace.expenses.ui.screen.today.component.ExpensesTodayShimmerList
import ru.fav.moneytrace.expenses.ui.screen.today.component.ExpensesTodayTotalItem
import ru.fav.moneytrace.expenses.ui.screen.today.component.ExpensesTodayTotalShimmerItem
import ru.fav.moneytrace.expenses.ui.screen.today.state.ExpensesTodayEvent
import ru.fav.moneytrace.ui.component.MTCenterAlignedTopAppBar
import ru.fav.moneytrace.ui.component.MTErrorDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesTodayScreen(
    onHistoryClick: () -> Unit,
    viewModel: ExpensesTodayViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MTCenterAlignedTopAppBar(
            title = stringResource(R.string.expenses_today),
            actions = {
                MTIconButton(
                    onClick = onHistoryClick
                ) {
                    MTIcon(
                        painter = painterResource(ru.fav.moneytrace.ui.R.drawable.ic_history),
                        contentDescription = stringResource(ru.fav.moneytrace.ui.R.string.history),
                    )
                }
            }
        )

        Box(
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                if (state.isLoading) {
                    ExpensesTodayTotalShimmerItem()
                } else {
                    ExpensesTodayTotalItem(
                        totalSum = state.total,
                    )
                }

                HorizontalDivider()

                when {
                    state.isLoading -> {
                        ExpensesTodayShimmerList()
                    }
                    else -> {
                        ExpensesTodayList(
                            expenses = state.expenses,
                            onExpenseClick = { expense ->
                            }
                        )
                    }
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
                viewModel.reduce(ExpensesTodayEvent.Retry)
            },
            onDismiss = {
                viewModel.reduce(ExpensesTodayEvent.HideErrorDialog)
            }
        )
    }
}