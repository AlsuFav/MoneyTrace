package ru.fav.moneytrace.analysis.impl.ui.screen

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
import ru.fav.moneytrace.analysis.impl.ui.screen.component.AnalysisList
import ru.fav.moneytrace.analysis.impl.ui.screen.component.AnalysisShimmerList
import ru.fav.moneytrace.analysis.impl.ui.screen.component.AnalysisTotalItem
import ru.fav.moneytrace.analysis.impl.ui.screen.component.AnalysisTotalShimmerItem
import ru.fav.moneytrace.analysis.impl.ui.screen.state.AnalysisEvent
import ru.fav.moneytrace.transaction.api.model.TransactionType
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTCenterAlignedTopAppBar
import ru.fav.moneytrace.ui.component.MTDatePicker
import ru.fav.moneytrace.ui.component.MTErrorDialog
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTIconButton
import ru.fav.moneytrace.ui.component.MTPeriodItem
import ru.fav.moneytrace.ui.theme.Providers
import ru.fav.moneytrace.util.DateHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(
    transactionType: TransactionType,
    onBackClick: () -> Unit,
    onCategoryClick: (Int) -> Unit,
    viewModel: AnalysisViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.reduce(AnalysisEvent.LoadAnalysis(transactionType))
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
            title = stringResource(R.string.analysis),
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
            backgroundColor = Providers.color.surface,
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
                        viewModel.reduce(AnalysisEvent.ShowStartDatePicker)
                    },
                    onEndDateClick = {
                        viewModel.reduce(AnalysisEvent.ShowEndDatePicker)
                    },
                    containerColor = Providers.color.surface,
                    dateHasAccent = true,
                    accentColor = Providers.color.primary
                )

                HorizontalDivider()

                when {
                    state.isLoading -> {
                        AnalysisTotalShimmerItem()
                        HorizontalDivider()
                        AnalysisShimmerList()
                    }
                    else -> {
                        AnalysisTotalItem(
                            totalSum = state.total,
                        )

                        HorizontalDivider()

                        AnalysisList(
                            categories = state.categories,
                            onCategoryClick = onCategoryClick
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
                viewModel.reduce(AnalysisEvent.OnStartDateSelected(date, transactionType))
            },
            onDismiss = {
                viewModel.reduce(AnalysisEvent.HideDatePicker)
            },
            maxDate = DateHelper.parseDisplayDate(state.endDate)
        )
    }

    if (state.showEndDatePicker) {
        MTDatePicker(
            selectedDate = DateHelper.parseDisplayDate(state.endDate),
            onDateSelected = { date ->
                viewModel.reduce(AnalysisEvent.OnEndDateSelected(date, transactionType))
            },
            onDismiss = {
                viewModel.reduce(AnalysisEvent.HideDatePicker)
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
                viewModel.reduce(AnalysisEvent.LoadAnalysis(transactionType))
            },
            onDismiss = {
                viewModel.reduce(AnalysisEvent.HideErrorDialog)
            }
        )
    }
}