package ru.fav.moneytrace.analysis.impl.ui.screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTShimmerListItem
import ru.fav.moneytrace.ui.theme.Providers

@Composable
fun AnalysisTotalShimmerItem() {
    Column(modifier = Modifier.fillMaxWidth()) {
        MTShimmerListItem(
            title = stringResource(R.string.sum),
            showTrailingTitle = true,
            height = Providers.spacing.xxl,
        )
    }
}