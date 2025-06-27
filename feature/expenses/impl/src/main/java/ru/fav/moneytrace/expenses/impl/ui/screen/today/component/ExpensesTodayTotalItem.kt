package ru.fav.moneytrace.expenses.impl.ui.screen.today.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTListItem
import ru.fav.moneytrace.ui.theme.Providers

/**
 * Composable функция для отображения итоговой суммы расходов за день.
 *
 * @param totalSum Строковое представление итоговой суммы расходов
 * @param modifier Модификатор для настройки внешнего вида и поведения компонента (по умолчанию Modifier)
 */


@Composable
fun ExpensesTodayTotalItem(
    totalSum: String,
    modifier: Modifier = Modifier
) {
    MTListItem(
        title = stringResource(R.string.total),
        trailingTitle = totalSum,
        onClick = { },
        height = Providers.spacing.xxl,
        backgroundColor = Providers.color.secondaryContainer,
        modifier = modifier
    )
}