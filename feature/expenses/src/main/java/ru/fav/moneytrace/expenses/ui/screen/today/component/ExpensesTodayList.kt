package ru.fav.moneytrace.expenses.ui.screen.today.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.fav.moneytrace.expenses.ui.model.ExpenseUIModel
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTEmojiIcon
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTListItem
import ru.fav.moneytrace.ui.theme.Providers

@Composable
fun ExpensesTodayList(
    expenses: List<ExpenseUIModel>,
    onExpenseClick: (ExpenseUIModel) -> Unit,
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
                trailingIcon = {
                    MTIcon(
                        painter = painterResource(R.drawable.ic_more),
                        contentDescription = stringResource(R.string.more),
                    )
                },
                onClick = { onExpenseClick(expense) }
            )
            HorizontalDivider()
        }
        item {
            Spacer(modifier = Modifier.height(Providers.spacing.xxxl))
        }
    }
}