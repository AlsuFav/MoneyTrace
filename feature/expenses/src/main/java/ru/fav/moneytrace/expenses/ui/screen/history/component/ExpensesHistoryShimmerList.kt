package ru.fav.moneytrace.expenses.ui.screen.history.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.fav.moneytrace.ui.component.MTShimmerListItem

@Composable
fun ExpensesHistoryShimmerList(
    modifier: Modifier = Modifier,
    itemCount: Int = 6,
) {
    LazyColumn(modifier = modifier) {
        items(count = itemCount) { index ->
            MTShimmerListItem(
                showLeadingIcon = true,
                showSubtitle = index % 2 == 1,
                showTrailingTitle = true,
                showTrailingSubtitle = true,
                showTrailingIcon = true
            )
            HorizontalDivider()
        }
    }
}