package ru.fav.moneytrace.expenses.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.fav.moneytrace.base.component.MTEmojiIcon
import ru.fav.moneytrace.base.component.MTIcon
import ru.fav.moneytrace.base.component.MTIconButton
import ru.fav.moneytrace.base.component.MTListItem
import ru.fav.moneytrace.base.component.MTTextField
import ru.fav.moneytrace.base.extension.toCurrencySymbol
import ru.fav.moneytrace.base.state.TopAppBarState
import ru.fav.moneytrace.base.theme.Providers
import ru.fav.moneytrace.expenses.R

@Composable
fun ExpensesScreen(
    topAppBarSetter: (TopAppBarState) -> Unit,
    viewModel: ExpensesViewModel = hiltViewModel()
    ) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val topAppBarState = TopAppBarState(
        isVisible = true,
        title = stringResource(R.string.expenses_today),
        actions = {
            MTIconButton(
                onClick = { }
            ) {
                MTIcon(
                    painter = painterResource(ru.fav.moneytrace.base.R.drawable.ic_history),
                    contentDescription = stringResource(ru.fav.moneytrace.base.R.string.history),
                )
            }
        }
    )

    LaunchedEffect(topAppBarState) {
        topAppBarSetter(topAppBarState)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        MTListItem(
            title = stringResource(ru.fav.moneytrace.base.R.string.total),
            trailingTitle = "${state.total} ${state.currency.toCurrencySymbol()}",
            onClick = { },
            textPadding = PaddingValues(
                horizontal = Providers.spacing.none,
                vertical = Providers.spacing.m
            ),
            backgroundColor = Providers.color.secondaryContainer
        )

        HorizontalDivider()

        LazyColumn {
            items(
                count = state.expenses.size,
                key = { index -> state.expenses[index].id }
            ) { index ->
                val expense = state.expenses[index]
                MTListItem(
                    leadingIcon = { MTEmojiIcon(expense.emoji) },
                    title = expense.category,
                    subtitle = expense.comment,
                    trailingTitle =  "${expense.amount} ${expense.currency.toCurrencySymbol()}",
                    trailingIcon = {
                        MTIcon(
                            painter = painterResource(ru.fav.moneytrace.base.R.drawable.ic_more),
                            contentDescription = stringResource(ru.fav.moneytrace.base.R.string.more),
                        )
                    },
                    onClick = { }
                )
                HorizontalDivider()
            }
        }
    }
}