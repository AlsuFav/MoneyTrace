package ru.fav.moneytrace.account.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.fav.moneytrace.ui.component.MTShimmerListItem
import ru.fav.moneytrace.ui.theme.Providers

@Composable
fun AccountShimmerItem() {
    Column(modifier = Modifier.fillMaxWidth()) {
        MTShimmerListItem(
            showLeadingIcon = true,
            showTrailingTitle = true,
            showTrailingIcon = true,
            height = Providers.spacing.xxl,
            backgroundColor = Providers.color.secondaryContainer
        )

        HorizontalDivider()

        MTShimmerListItem(
            showTrailingTitle = true,
            showTrailingIcon = true,
            height = Providers.spacing.xxl,
            backgroundColor = Providers.color.secondaryContainer
        )
    }
}