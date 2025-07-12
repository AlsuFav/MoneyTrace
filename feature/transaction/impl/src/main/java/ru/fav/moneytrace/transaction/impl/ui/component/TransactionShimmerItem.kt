package ru.fav.moneytrace.transaction.impl.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.fav.moneytrace.ui.component.MTShimmerListItem
import ru.fav.moneytrace.ui.theme.Providers

@Composable
fun TransactionShimmerItem() {
    Column(modifier = Modifier.fillMaxWidth()) {
        MTShimmerListItem(
            showTrailingTitle = true,
            showTrailingIcon = true,
        )

        HorizontalDivider()

        MTShimmerListItem(
            showTrailingTitle = true,
            showTrailingIcon = true,
        )

        HorizontalDivider()

        MTShimmerListItem(
            showTrailingTitle = true,
        )

        HorizontalDivider()

        MTShimmerListItem(
            showTrailingTitle = true,
        )

        HorizontalDivider()

        MTShimmerListItem(
            showTrailingTitle = true,
        )

        HorizontalDivider()

        MTShimmerListItem()

        HorizontalDivider()
    }
}