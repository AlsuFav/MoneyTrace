package ru.fav.moneytrace.income.ui.screen.today.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import ru.fav.moneytrace.ui.component.MTShimmerListItem
import ru.fav.moneytrace.ui.theme.Providers

@Composable
fun IncomeTodayTotalShimmerItem() {
    MTShimmerListItem(
        showTrailingTitle = true,
        height = Providers.spacing.xxl,
        backgroundColor = Providers.color.secondaryContainer
    )
}