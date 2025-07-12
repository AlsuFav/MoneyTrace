package ru.fav.moneytrace.account.impl.ui.update.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import ru.fav.moneytrace.account.impl.ui.model.AccountUIModel
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTEditableListItem
import ru.fav.moneytrace.ui.component.MTEmojiIcon
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTListItem
import ru.fav.moneytrace.ui.theme.Providers
import ru.fav.moneytrace.ui.util.pattern.BALANCE_PATTERN

@Composable
fun AccountUpdateItem(
    account: AccountUIModel,
    onNameChange: (String) -> Unit,
    onBalanceChange: (String) -> Unit,
    onCurrencyClick: () -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        MTEditableListItem(
            leadingIcon = {
                MTEmojiIcon(
                    emoji = "\uD83D\uDCB0",
                )
            },
            placeholder = stringResource(R.string.account),
            title = stringResource(ru.fav.moneytrace.account.impl.R.string.account_name),
            value = account.name,
            onValueChange = onNameChange,
            height = Providers.spacing.xxl,
        )

        HorizontalDivider()

        MTEditableListItem(
            title = stringResource(ru.fav.moneytrace.account.impl.R.string.balance),
            value = account.balance,
            placeholder = "0.00",
            keyboardType = KeyboardType.Number,
            onValueChange = onBalanceChange,
            inputFilter = { newValue ->
                newValue.isEmpty() || BALANCE_PATTERN.matches(newValue)
            },
            height = Providers.spacing.xxl,
        )

        HorizontalDivider()

        MTListItem(
            title = stringResource(ru.fav.moneytrace.account.impl.R.string.valute),
            trailingTitle = account.currency.symbol,
            trailingIcon = {
                MTIcon(
                    painter = painterResource(R.drawable.ic_more),
                    contentDescription = stringResource(R.string.more),
                )
            },
            onClick = { onCurrencyClick() },
            height = Providers.spacing.xxl,
        )

        HorizontalDivider()
    }
}