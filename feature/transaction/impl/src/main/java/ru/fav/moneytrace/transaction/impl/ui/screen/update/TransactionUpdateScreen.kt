package ru.fav.moneytrace.transaction.impl.ui.screen.update

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import ru.fav.moneytrace.transaction.impl.ui.screen.update.state.TransactionUpdateEffect
import ru.fav.moneytrace.transaction.impl.ui.screen.update.state.TransactionUpdateEvent
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTButton
import ru.fav.moneytrace.ui.component.MTCenterAlignedTopAppBar
import ru.fav.moneytrace.ui.component.MTDatePicker
import ru.fav.moneytrace.ui.component.MTErrorDialog
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTIconButton
import ru.fav.moneytrace.ui.component.MTTimePicker
import ru.fav.moneytrace.ui.theme.Providers
import ru.fav.moneytrace.util.DateHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionUpdateScreen(
    transactionId: Int,
    transactionType: TransactionType,
    onCloseClick: () -> Unit,
    onSaveClick: () -> Unit,
    viewModel: TransactionUpdateViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val topBarTitle = when (transactionType) {
        TransactionType.EXPENSE -> stringResource(ru.fav.moneytrace.transaction.impl.R.string.my_expenses)
        TransactionType.INCOME -> stringResource(ru.fav.moneytrace.transaction.impl.R.string.my_income)
        TransactionType.ANY -> stringResource(ru.fav.moneytrace.transaction.impl.R.string.my_transaction)
    }

    val deleteButtonText = when (transactionType) {
        TransactionType.EXPENSE -> stringResource(ru.fav.moneytrace.transaction.impl.R.string.delete_expense)
        TransactionType.INCOME -> stringResource(ru.fav.moneytrace.transaction.impl.R.string.delete_income)
        TransactionType.ANY -> stringResource(ru.fav.moneytrace.transaction.impl.R.string.delete_transaction)
    }

    LaunchedEffect(Unit) {
        viewModel.reduce(TransactionUpdateEvent.LoadData(transactionId, transactionType))
    }

    val context = LocalContext.current
    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TransactionUpdateEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                is TransactionUpdateEffect.TransactionUpdated -> onSaveClick()
                is TransactionUpdateEffect.TransactionDeleted -> onSaveClick()
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
                    onClick = { viewModel.reduce(TransactionUpdateEvent.OnDoneClicked) }
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
            Column {
                when {
                    state.isLoading -> {
                        TransactionShimmerItem()
                    }
                    else -> {
                        TransactionItem(
                            transaction = state.transaction,
                            onAmountChange = { newAmount ->
                                viewModel.reduce(TransactionUpdateEvent.UpdateAmount(newAmount))
                            },
                            onCommentChange = { newComment ->
                                viewModel.reduce(TransactionUpdateEvent.UpdateComment(newComment))
                            },
                            onCategoryClick = {
                                viewModel.reduce(TransactionUpdateEvent.ShowCategoryBottomSheet)
                            },
                            onDateClick = {
                                viewModel.reduce(TransactionUpdateEvent.ShowDatePicker)
                            },
                            onTimeClick = {
                                viewModel.reduce(TransactionUpdateEvent.ShowTimePicker)
                            },
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Providers.spacing.xl))

                MTButton(
                    onClick = {
                        viewModel.reduce(TransactionUpdateEvent.OnDeleteClicked(transactionId))
                    },
                    containerColor = Providers.color.error,
                    contentColor = Providers.color.onError,
                    text = deleteButtonText,
                    modifier = Modifier.padding(horizontal = Providers.spacing.m)
                )
            }
        }
    }

    if (state.showBottomSheet) {
        CategorySelectionBottomSheet(
            categories = state.categories,
            onCategorySelected = { category ->
                viewModel.reduce(TransactionUpdateEvent.SelectCategory(category))
                viewModel.reduce(TransactionUpdateEvent.HideCategoryBottomSheet)
            },
            onDismiss = {
                viewModel.reduce(TransactionUpdateEvent.HideCategoryBottomSheet)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (state.showDatePicker) {
        MTDatePicker(
            selectedDate = DateHelper.parseDisplayDate(state.transaction.date),
            onDateSelected = { date ->
                viewModel.reduce(TransactionUpdateEvent.OnDateSelected(date))
            },
            onDismiss = {
                viewModel.reduce(TransactionUpdateEvent.HideDatePicker)
            },
        )
    }

    if (state.showTimePicker) {
        MTTimePicker(
            selectedTime = DateHelper.parseDisplayDate(state.transaction.time),
            onTimeSelected = { time ->
                viewModel.reduce(TransactionUpdateEvent.OnTimeSelected(time))
            },
            onDismiss = {
                viewModel.reduce(TransactionUpdateEvent.HideTimePicker)
            },
        )
    }

    state.showErrorDialog?.let { message ->
        MTErrorDialog(
            message = message,
            confirmButtonText = stringResource(R.string.repeat),
            dismissButtonText = stringResource(R.string.exit),
            onConfirm = {
                viewModel.reduce(TransactionUpdateEvent.LoadData(transactionId, transactionType))
            },
            onDismiss = {
                viewModel.reduce(TransactionUpdateEvent.HideErrorDialog)
            }
        )
    }
}