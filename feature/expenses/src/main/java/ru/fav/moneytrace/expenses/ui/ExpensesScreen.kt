package ru.fav.moneytrace.expenses.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.fav.moneytrace.ui.component.MTEmojiIcon
import ru.fav.moneytrace.ui.component.MTFloatingActionButton
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTIconButton
import ru.fav.moneytrace.ui.component.MTListItem
import ru.fav.moneytrace.util.extension.toCurrencySymbol
import ru.fav.moneytrace.ui.theme.Providers
import ru.fav.moneytrace.expenses.R
import ru.fav.moneytrace.ui.component.MTCenterAlignedTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(
    viewModel: ExpensesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            MTCenterAlignedTopAppBar(
                title = stringResource(R.string.expenses_today),
                actions = {
                    MTIconButton(
                        onClick = { }
                    ) {
                        MTIcon(
                            painter = painterResource(ru.fav.moneytrace.ui.R.drawable.ic_history),
                            contentDescription = stringResource(ru.fav.moneytrace.ui.R.string.history),
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            MTFloatingActionButton(
                onClick = { },
                contentDescription = stringResource(ru.fav.moneytrace.ui.R.string.add),
                painter = painterResource(ru.fav.moneytrace.ui.R.drawable.ic_add),
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                MTListItem(
                    title = stringResource(ru.fav.moneytrace.ui.R.string.total),
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
                            trailingTitle = "${expense.amount} ${expense.currency.toCurrencySymbol()}",
                            trailingIcon = {
                                MTIcon(
                                    painter = painterResource(ru.fav.moneytrace.ui.R.drawable.ic_more),
                                    contentDescription = stringResource(ru.fav.moneytrace.ui.R.string.more),
                                )
                            },
                            onClick = { }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}