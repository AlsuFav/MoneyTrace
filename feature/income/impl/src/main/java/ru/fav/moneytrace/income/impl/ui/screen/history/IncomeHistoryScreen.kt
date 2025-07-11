package ru.fav.moneytrace.income.impl.ui.screen.history

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
import ru.fav.moneytrace.income.impl.ui.screen.history.component.IncomeHistoryTotalItem
import ru.fav.moneytrace.income.impl.ui.screen.history.component.IncomeHistoryTotalShimmerItem
import ru.fav.moneytrace.income.impl.ui.screen.history.component.IncomeHistoryList
import ru.fav.moneytrace.income.impl.ui.screen.history.component.IncomeHistoryShimmerList
import ru.fav.moneytrace.income.impl.ui.screen.history.state.IncomeHistoryEvent
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTIconButton
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTCenterAlignedTopAppBar
import ru.fav.moneytrace.ui.component.MTDatePicker
import ru.fav.moneytrace.ui.component.MTErrorDialog
import ru.fav.moneytrace.ui.component.MTPeriodItem
import ru.fav.moneytrace.util.DateHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeHistoryScreen(
    onBackClick: () -> Unit,
    onIncomeClick: (Int) -> Unit,
    viewModel: IncomeHistoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.reduce(IncomeHistoryEvent.LoadIncome)
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
                    onClick = { }
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
                        viewModel.reduce(IncomeHistoryEvent.ShowStartDatePicker)
                    },
                    onEndDateClick = {
                        viewModel.reduce(IncomeHistoryEvent.ShowEndDatePicker)
                    },
                )

                HorizontalDivider()

                when {
                    state.isLoading -> {
                        IncomeHistoryTotalShimmerItem()
                        HorizontalDivider()
                        IncomeHistoryShimmerList()
                    }
                    else -> {
                        IncomeHistoryTotalItem(
                            totalSum = state.total,
                        )

                        HorizontalDivider()

                        IncomeHistoryList(
                            expenses = state.income,
                            onIncomeClick = onIncomeClick
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
                viewModel.reduce(IncomeHistoryEvent.OnStartDateSelected(date))
            },
            onDismiss = {
                viewModel.reduce(IncomeHistoryEvent.HideDatePicker)
            },
            maxDate = DateHelper.parseDisplayDate(state.endDate)
        )
    }

    if (state.showEndDatePicker) {
        MTDatePicker(
            selectedDate = DateHelper.parseDisplayDate(state.endDate),
            onDateSelected = { date ->
                viewModel.reduce(IncomeHistoryEvent.OnEndDateSelected(date))
            },
            onDismiss = {
                viewModel.reduce(IncomeHistoryEvent.HideDatePicker)
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
                viewModel.reduce(IncomeHistoryEvent.LoadIncome)
            },
            onDismiss = {
                viewModel.reduce(IncomeHistoryEvent.HideErrorDialog)
            }
        )
    }
}