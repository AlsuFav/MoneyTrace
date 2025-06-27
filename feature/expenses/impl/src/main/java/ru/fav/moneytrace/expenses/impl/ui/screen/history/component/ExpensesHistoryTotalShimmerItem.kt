package ru.fav.moneytrace.expenses.impl.ui.screen.history.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTShimmerListItem
import ru.fav.moneytrace.ui.theme.Providers

@Composable
fun ExpensesHistoryTotalShimmerItem() {
    Column(modifier = Modifier.fillMaxWidth()) {
        MTShimmerListItem(
            title = stringResource(R.string.sum),
            showTrailingTitle = true,
            height = Providers.spacing.xxl,
            backgroundColor = Providers.color.secondaryContainer
        )
    }
}