package ru.fav.moneytrace.transaction.impl.ui.component
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import ru.fav.moneytrace.transaction.impl.ui.model.TransactionUIModel
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTEditableListItem
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTListItem
import ru.fav.moneytrace.ui.theme.Providers
import ru.fav.moneytrace.ui.util.pattern.BALANCE_PATTERN

@Composable
fun TransactionItem(
    transaction: TransactionUIModel,
    onAmountChange: (String) -> Unit,
    onCategoryClick: () -> Unit,
    onDateClick: () -> Unit,
    onTimeClick: () -> Unit,
    onCommentChange: (String) -> Unit,
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        MTListItem(
            title = stringResource(R.string.account),
            trailingTitle = transaction.account.name,
            trailingIcon = {
                MTIcon(
                    painter = painterResource(R.drawable.ic_more),
                    contentDescription = stringResource(R.string.more),
                )
            },
        )

        HorizontalDivider()

        MTListItem(
            title = stringResource(ru.fav.moneytrace.transaction.impl.R.string.category),
            trailingTitle = transaction.category.name,
            trailingIcon = {
                MTIcon(
                    painter = painterResource(R.drawable.ic_more),
                    contentDescription = stringResource(R.string.more),
                )
            },
            onClick = { onCategoryClick() },
        )

        HorizontalDivider()

        MTEditableListItem(
            title = stringResource(R.string.sum),
            value = transaction.amount,
            trailingTitle = " ${transaction.account.currencySymbol}",
            placeholder = "0.00",
            keyboardType = KeyboardType.Number,
            onValueChange = onAmountChange,
            inputFilter = { newValue ->
                newValue.isEmpty() || BALANCE_PATTERN.matches(newValue)
            },
        )

        HorizontalDivider()

        MTListItem(
            title = stringResource(ru.fav.moneytrace.transaction.impl.R.string.date),
            trailingTitle = transaction.date,
            onClick = { onDateClick() },
        )

        HorizontalDivider()

        MTListItem(
            title = stringResource(ru.fav.moneytrace.transaction.impl.R.string.time),
            trailingTitle = transaction.time,
            onClick = { onTimeClick() },
        )

        HorizontalDivider()

        MTEditableListItem(
            value = transaction.comment,
            placeholder = stringResource(ru.fav.moneytrace.transaction.impl.R.string.comment),
            onValueChange = onCommentChange,
        )

        HorizontalDivider()
    }
}