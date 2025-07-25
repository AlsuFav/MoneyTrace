package ru.fav.moneytrace.expenses.impl.ui.screen.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.fav.moneytrace.expenses.impl.ui.screen.history.component.ExpensesHistoryTotalItem
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTIconButton
import ru.fav.moneytrace.expenses.impl.ui.screen.history.component.ExpensesHistoryList
import ru.fav.moneytrace.expenses.impl.ui.screen.history.component.ExpensesHistoryShimmerList
import ru.fav.moneytrace.expenses.impl.ui.screen.history.component.ExpensesHistoryTotalShimmerItem
import ru.fav.moneytrace.expenses.impl.ui.screen.history.state.ExpensesHistoryEvent
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTCenterAlignedTopAppBar
import ru.fav.moneytrace.ui.component.MTDatePicker
import ru.fav.moneytrace.ui.component.MTErrorDialog
import ru.fav.moneytrace.ui.component.MTPeriodItem
import ru.fav.moneytrace.util.DateHelper

/**
 * Composable функция для отображения экрана истории расходов.
 *
 * Экран содержит верхнюю панель с кнопками навигации и анализа, селектор периода дат,
 * общую сумму расходов и список расходов за выбранный период. Также включает диалоги
 * для выбора дат и отображения ошибок.
 *
 * @param onBackClick Функция обратного вызова при нажатии кнопки "Назад"
 * @param viewModel ViewModel для управления состоянием и бизнес-логикой экрана (по умолчанию предоставляется через Hilt)
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesHistoryScreen(
    onBackClick: () -> Unit,
    onAnalysisClick: () -> Unit,
    onExpenseClick: (Int) -> Unit,
    viewModel: ExpensesHistoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.reduce(ExpensesHistoryEvent.LoadExpenses)
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
            title = stringResource(R.string.my_history),
            navigationIcon = {
                MTIconButton(
                    onClick = onBackClick
                ) {
                    MTIcon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = stringResource(R.string.back),
                    )
                }
            },
            actions = {
                MTIconButton(
                    onClick = onAnalysisClick
                ) {
                    MTIcon(
                        painter = painterResource(R.drawable.ic_analysis),
                        contentDescription = stringResource(R.string.analysis),
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
                MTPeriodItem(
                    startDate = state.startDate,
                    endDate = state.endDate,
                    onStartDateClick = {
                        viewModel.reduce(ExpensesHistoryEvent.ShowStartDatePicker)
                    },
                    onEndDateClick = {
                        viewModel.reduce(ExpensesHistoryEvent.ShowEndDatePicker)
                    },
                )

                HorizontalDivider()

                when {
                    state.isLoading -> {
                        ExpensesHistoryTotalShimmerItem()
                        HorizontalDivider()
                        ExpensesHistoryShimmerList()
                    }
                    else -> {
                        ExpensesHistoryTotalItem(
                            totalSum = state.total,
                        )

                        HorizontalDivider()

                        ExpensesHistoryList(
                            expenses = state.expenses,
                            onExpenseClick = onExpenseClick
                        )
                    }
                }
            }
        }
    }

    if (state.showStartDatePicker) {
        MTDatePicker(
            selectedDate = DateHelper.parseDisplayDate(state.startDate),
            onDateSelected = { date ->
                viewModel.reduce(ExpensesHistoryEvent.OnStartDateSelected(date))
            },
            onDismiss = {
                viewModel.reduce(ExpensesHistoryEvent.HideDatePicker)
            },
            maxDate = DateHelper.parseDisplayDate(state.endDate)
        )
    }

    if (state.showEndDatePicker) {
        MTDatePicker(
            selectedDate = DateHelper.parseDisplayDate(state.endDate),
            onDateSelected = { date ->
                viewModel.reduce(ExpensesHistoryEvent.OnEndDateSelected(date))
            },
            onDismiss = {
                viewModel.reduce(ExpensesHistoryEvent.HideDatePicker)
            },
            minDate = DateHelper.parseDisplayDate(state.startDate)
        )
    }

    state.showErrorDialog?.let { message ->
        MTErrorDialog(
            message = message,
            confirmButtonText = stringResource(R.string.repeat),
            dismissButtonText = stringResource(R.string.exit),
            onConfirm = {
                viewModel.reduce(ExpensesHistoryEvent.LoadExpenses)
            },
            onDismiss = {
                viewModel.reduce(ExpensesHistoryEvent.HideErrorDialog)
            }
        )
    }
}