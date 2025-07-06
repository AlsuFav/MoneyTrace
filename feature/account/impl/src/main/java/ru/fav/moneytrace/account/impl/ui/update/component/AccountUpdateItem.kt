package ru.fav.moneytrace.account.impl.ui.update.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.fav.moneytrace.account.impl.ui.model.AccountUIModel
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTEmojiIcon
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTListItem
import ru.fav.moneytrace.ui.theme.Providers

@Composable
fun AccountUpdateItem(
    account: AccountUIModel,
    onCurrencyClick: () -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        MTListItem(
            leadingIcon = {
                MTEmojiIcon(
                    emoji = "\uD83D\uDCB0",
                )
            },
            title = stringResource(ru.fav.moneytrace.account.impl.R.string.account_name),
            trailingTitle = if (account.name.isEmpty()) stringResource(R.string.account) else account.name,
            onClick = { },
            height = Providers.spacing.xxl,
        )

        HorizontalDivider()

        MTListItem(
            title = stringResource(ru.fav.moneytrace.account.impl.R.string.balance),
            trailingTitle = account.balance,
            onClick = { },
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