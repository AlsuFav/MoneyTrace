package ru.fav.moneytrace.income.impl.ui.screen.today.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.fav.moneytrace.ui.component.MTShimmerListItem

@Composable
fun IncomeTodayShimmerList(
    modifier: Modifier = Modifier,
    itemCount: Int = 6,
) {
    LazyColumn {
        items(count = itemCount) { index ->
            MTShimmerListItem(
                showSubtitle = index % 2 == 1,
                showTrailingTitle = true,
                showTrailingIcon = true
            )
            HorizontalDivider()
        }
    }
}