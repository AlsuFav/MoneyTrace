package ru.fav.moneytrace.analysis.impl.ui.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTListItem
import ru.fav.moneytrace.ui.theme.Providers

@Composable
fun AnalysisTotalItem(
    totalSum: String,
    modifier: Modifier = Modifier
) {
    MTListItem(
        title = stringResource(R.string.sum),
        trailingTitle = totalSum,
        onClick = { },
        height = Providers.spacing.xxl,
        modifier = modifier
    )
}
