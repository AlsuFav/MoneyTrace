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

/**
 * Компонент для выбора периода дат (начальная и конечная дата).
 *
 * @param startDate Отформатированная начальная дата
 * @param endDate Отформатированная конечная дата
 * @param onStartDateClick Callback при нажатии на начальную дату
 * @param onEndDateClick Callback при нажатии на конечную дату
 * @param modifier Модификатор для настройки внешнего вида
 */

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