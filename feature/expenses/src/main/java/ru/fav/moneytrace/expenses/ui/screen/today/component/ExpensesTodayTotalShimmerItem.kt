package ru.fav.moneytrace.expenses.ui.screen.today.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTShimmerListItem
import ru.fav.moneytrace.ui.theme.Providers

@Composable
fun ExpensesTodayTotalShimmerItem() {
    MTShimmerListItem(
        showTrailingTitle = true,
        height = Providers.spacing.xxl,
        backgroundColor = Providers.color.secondaryContainer
    )
}