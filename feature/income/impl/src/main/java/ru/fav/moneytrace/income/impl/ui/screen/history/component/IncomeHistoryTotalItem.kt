package ru.fav.moneytrace.income.impl.ui.screen.history.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTListItem
import ru.fav.moneytrace.ui.theme.Providers

/**
 * Composable функция для отображения итоговой суммы доходов в истории.
 *
 * @param totalSum Строковое представление итоговой суммы доходов
 * @param modifier Модификатор для настройки внешнего вида и поведения компонента (по умолчанию Modifier)
 */


@Composable
fun IncomeHistoryTotalItem(
    totalSum: String,
    modifier: Modifier = Modifier
) {
    MTListItem(
        title = stringResource(R.string.sum),
        trailingTitle = totalSum,
        onClick = { },
        height = Providers.spacing.xxl,
        backgroundColor = Providers.color.secondaryContainer,
        modifier = modifier
    )
}
