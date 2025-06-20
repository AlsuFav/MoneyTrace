package ru.fav.moneytrace.expenses.ui.screen.history.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTListItem
import ru.fav.moneytrace.ui.theme.Providers

@Composable
fun ExpensesHistoryTotalItem(
    totalSum: String,
    modifier: Modifier = Modifier
) {
    MTListItem(
        title = stringResource(R.string.sum),
        trailingTitle = totalSum,
        onClick = { },
        textPadding = PaddingValues(
            horizontal = Providers.spacing.none,
            vertical = Providers.spacing.m
        ),
        backgroundColor = Providers.color.secondaryContainer,
        modifier = modifier
    )
}
