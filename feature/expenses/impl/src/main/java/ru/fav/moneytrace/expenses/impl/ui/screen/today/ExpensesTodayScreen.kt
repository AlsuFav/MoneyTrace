package ru.fav.moneytrace.expenses.impl.ui.screen.today

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import ru.fav.moneytrace.ui.component.MTFloatingActionButton
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTIconButton
import ru.fav.moneytrace.ui.theme.Providers
import ru.fav.moneytrace.expenses.impl.R
import ru.fav.moneytrace.expenses.impl.ui.screen.today.component.ExpensesTodayList
import ru.fav.moneytrace.expenses.impl.ui.screen.today.component.ExpensesTodayShimmerList
import ru.fav.moneytrace.expenses.impl.ui.screen.today.component.ExpensesTodayTotalItem
import ru.fav.moneytrace.expenses.impl.ui.screen.today.component.ExpensesTodayTotalShimmerItem
import ru.fav.moneytrace.expenses.impl.ui.screen.today.state.ExpensesTodayEvent
import ru.fav.moneytrace.ui.component.MTCenterAlignedTopAppBar
import ru.fav.moneytrace.ui.component.MTErrorDialog

/**
 * Composable функция для отображения экрана расходов за сегодняшний день.
 *
 * Экран содержит верхнюю панель с заголовком и кнопкой перехода к истории,
 * общую сумму расходов за день и список расходов. Также включает плавающую кнопку
 * для добавления новых расходов и диалог для отображения ошибок.
 *
 * @param onHistoryClick Функция обратного вызова при нажатии кнопки "История"
 * @param viewModel ViewModel для управления состоянием и бизнес-логикой экрана (по умолчанию предоставляется через Hilt)
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesTodayScreen(
    onHistoryClick: () -> Unit,
    viewModel: ExpensesTodayViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.reduce(ExpensesTodayEvent.LoadExpenses)
    }

    DisposableEffect(viewModel) {
        onDispose {
            viewModel.cancelAllTasks()
        }
    }

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
                when {
                    state.isLoading -> {
                        ExpensesTodayTotalShimmerItem()
                        HorizontalDivider()
                        ExpensesTodayShimmerList()
                    }
                    else -> {
                        ExpensesTodayTotalItem(
                            totalSum = state.total,
                        )

                        HorizontalDivider()

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
                viewModel.reduce(ExpensesTodayEvent.LoadExpenses)
            },
            onDismiss = {
                viewModel.reduce(ExpensesTodayEvent.HideErrorDialog)
            }
        )
    }
}