package ru.fav.moneytrace.transaction.impl.ui.screen.create

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
import ru.fav.moneytrace.transaction.api.model.TransactionType
import ru.fav.moneytrace.transaction.impl.ui.component.CategorySelectionBottomSheet
import ru.fav.moneytrace.transaction.impl.ui.component.TransactionItem
import ru.fav.moneytrace.transaction.impl.ui.component.TransactionShimmerItem
import ru.fav.moneytrace.transaction.impl.ui.screen.create.state.TransactionCreateEffect
import ru.fav.moneytrace.transaction.impl.ui.screen.create.state.TransactionCreateEvent
import ru.fav.moneytrace.transaction.impl.ui.screen.update.TransactionUpdateViewModel
import ru.fav.moneytrace.transaction.impl.ui.screen.update.state.TransactionUpdateEffect
import ru.fav.moneytrace.transaction.impl.ui.screen.update.state.TransactionUpdateEvent
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTCenterAlignedTopAppBar
import ru.fav.moneytrace.ui.component.MTDatePicker
import ru.fav.moneytrace.ui.component.MTErrorDialog
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTIconButton
import ru.fav.moneytrace.ui.component.MTTimePicker
import ru.fav.moneytrace.util.DateHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionCreateScreen(
    transactionType: TransactionType,
    onCloseClick: () -> Unit,
    onSaveClick: () -> Unit,
    viewModel: TransactionCreateViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val topBarTitle = when (transactionType) {
        TransactionType.EXPENSE -> stringResource(ru.fav.moneytrace.transaction.impl.R.string.my_expenses)
        TransactionType.INCOME -> stringResource(ru.fav.moneytrace.transaction.impl.R.string.my_income)
        TransactionType.ANY -> stringResource(ru.fav.moneytrace.transaction.impl.R.string.my_transaction)
    }

    LaunchedEffect(Unit) {
        viewModel.reduce(TransactionCreateEvent.LoadData(transactionType))
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TransactionCreateEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                is TransactionCreateEffect.TransactionCreated -> onSaveClick()
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
            title = topBarTitle,
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
                    onClick = { viewModel.reduce(TransactionCreateEvent.OnDoneClicked) }
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
                    TransactionShimmerItem()
                }
                else -> {
                    TransactionItem(
                        transaction = state.transaction,
                        onAmountChange = { newAmount ->
                            viewModel.reduce(TransactionCreateEvent.UpdateAmount(newAmount))
                        },
                        onCommentChange = { newComment ->
                            viewModel.reduce(TransactionCreateEvent.UpdateComment(newComment))
                        },
                        onCategoryClick = {
                            viewModel.reduce(TransactionCreateEvent.ShowCategoryBottomSheet)
                        },
                        onDateClick = {
                            viewModel.reduce(TransactionCreateEvent.ShowDatePicker)
                        },
                        onTimeClick = {
                            viewModel.reduce(TransactionCreateEvent.ShowTimePicker)
                        },
                    )
                }
            }
        }
    }

    if (state.showBottomSheet) {
        CategorySelectionBottomSheet(
            categories = state.categories,
            onCategorySelected = { category ->
                viewModel.reduce(TransactionCreateEvent.SelectCategory(category))
                viewModel.reduce(TransactionCreateEvent.HideCategoryBottomSheet)
            },
            onDismiss = {
                viewModel.reduce(TransactionCreateEvent.HideCategoryBottomSheet)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (state.showDatePicker) {
        MTDatePicker(
            selectedDate = DateHelper.parseDisplayDate(state.transaction.date),
            onDateSelected = { date ->
                viewModel.reduce(TransactionCreateEvent.OnDateSelected(date))
            },
            onDismiss = {
                viewModel.reduce(TransactionCreateEvent.HideDatePicker)
            },
        )
    }

    if (state.showTimePicker) {
        MTTimePicker(
            selectedTime = DateHelper.parseDisplayDate(state.transaction.time),
            onTimeSelected = { time ->
                viewModel.reduce(TransactionCreateEvent.OnTimeSelected(time))
            },
            onDismiss = {
                viewModel.reduce(TransactionCreateEvent.HideTimePicker)
            },
        )
    }

    state.showErrorDialog?.let { message ->
        MTErrorDialog(
            message = message,
            confirmButtonText = stringResource(R.string.repeat),
            dismissButtonText = stringResource(R.string.exit),
            onConfirm = {
                viewModel.reduce(TransactionCreateEvent.LoadData(transactionType))
            },
            onDismiss = {
                viewModel.reduce(TransactionCreateEvent.HideErrorDialog)
            }
        )
    }
}