package ru.fav.moneytrace.expenses.impl.ui.screen.history.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.fav.moneytrace.expenses.impl.ui.model.ExpenseUIModel
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTEmojiIcon
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTListItem

/**
 * Список расходов за период с детальной информацией.
 *
 * @param expenses Список расходов для отображения
 * @param onExpenseClick Callback при нажатии на элемент расхода
 * @param modifier Модификатор для настройки внешнего вида
 */

@Composable
fun ExpensesHistoryList(
    expenses: List<ExpenseUIModel>,
    onExpenseClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(
            count = expenses.size,
            key = { index -> expenses[index].id }
        ) { index ->
            val expense = expenses[index]
            MTListItem(
                leadingIcon = { 
                    MTEmojiIcon(
                        emoji = expense.emoji
                    ) 
                },
                title = expense.name,
                subtitle = expense.comment,
                trailingTitle = expense.amount,
                trailingSubtitle = expense.date,
                trailingIcon = {
                    MTIcon(
                        painter = painterResource(R.drawable.ic_more),
                        contentDescription = stringResource(R.string.more),
                    )
                },
                onClick = { onExpenseClick(expense.id) }
            )
            HorizontalDivider()
        }
    }
}