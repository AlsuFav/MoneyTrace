package ru.fav.moneytrace.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.theme.Providers

@Composable
fun MTPeriodItem(
    startDate: String,
    endDate: String,
    onStartDateClick: () -> Unit,
    onEndDateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        MTListItem(
            title = stringResource(R.string.start),
            trailingTitle = startDate,
            onClick = onStartDateClick,
            height = Providers.spacing.xxl,
            backgroundColor = Providers.color.secondaryContainer
        )

        HorizontalDivider()

        MTListItem(
            title = stringResource(R.string.end),
            trailingTitle = endDate,
            onClick = onEndDateClick,
            height = Providers.spacing.xxl,
            backgroundColor = Providers.color.secondaryContainer
        )
    }
}