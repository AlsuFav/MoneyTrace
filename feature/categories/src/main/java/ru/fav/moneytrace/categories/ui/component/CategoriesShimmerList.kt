package ru.fav.moneytrace.categories.ui.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.fav.moneytrace.ui.component.MTShimmerListItem

@Composable
fun CategoriesShimmerList(
    modifier: Modifier = Modifier,
    itemCount: Int = 6,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(count = itemCount) { index ->
            MTShimmerListItem(
                showLeadingIcon = true,
            )
            HorizontalDivider()
        }
    }
}