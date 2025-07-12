package ru.fav.moneytrace.income.impl.ui.screen.history.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.fav.moneytrace.income.impl.ui.model.IncomeUIModel
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTListItem

/**
 * Список доходов за период с детальной информацией.
 *
 * @param expenses Список доходов для отображения
 * @param onIncomeClick Callback при нажатии на элемент дохода
 * @param modifier Модификатор для настройки внешнего вида
 */


@Composable
fun IncomeHistoryList(
    expenses: List<IncomeUIModel>,
    onIncomeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(
            count = expenses.size,
            key = { index -> expenses[index].id }
        ) { index ->
            val income = expenses[index]
            MTListItem(
                title = income.name,
                subtitle = income.comment,
                trailingTitle = income.amount,
                trailingSubtitle = income.date,
                trailingIcon = {
                    MTIcon(
                        painter = painterResource(R.drawable.ic_more),
                        contentDescription = stringResource(R.string.more),
                    )
                },
                onClick = { onIncomeClick(income.id) }
            )
            HorizontalDivider()
        }
    }
}