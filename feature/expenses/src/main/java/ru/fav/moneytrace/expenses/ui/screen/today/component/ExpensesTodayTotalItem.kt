package ru.fav.moneytrace.expenses.ui.screen.today.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTListItem
import ru.fav.moneytrace.ui.theme.Providers

@Composable
fun ExpensesTodayTotalItem(
    totalSum: String,
    modifier: Modifier = Modifier
) {
    MTListItem(
        title = stringResource(R.string.total),
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