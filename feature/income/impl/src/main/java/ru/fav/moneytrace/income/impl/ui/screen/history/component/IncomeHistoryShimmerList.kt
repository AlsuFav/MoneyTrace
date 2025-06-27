package ru.fav.moneytrace.income.impl.ui.screen.history.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.fav.moneytrace.ui.component.MTShimmerListItem

@Composable
fun IncomeHistoryShimmerList(
    modifier: Modifier = Modifier,
    itemCount: Int = 6,
) {
    LazyColumn(modifier = modifier) {
        items(count = itemCount) { index ->
            MTShimmerListItem(
                showSubtitle = index % 2 == 1,
                showTrailingTitle = true,
                showTrailingSubtitle = true,
                showTrailingIcon = true
            )
            HorizontalDivider()
        }
    }
}