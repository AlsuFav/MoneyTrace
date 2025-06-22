package ru.fav.moneytrace.account.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.fav.moneytrace.account.R
import ru.fav.moneytrace.account.ui.model.AccountUIModel
import ru.fav.moneytrace.ui.component.MTEmojiIcon
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTListItem
import ru.fav.moneytrace.ui.theme.Providers


@Composable
fun AccountItem(account: AccountUIModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        MTListItem(
            leadingIcon = {
                MTEmojiIcon(
                    emoji = "\uD83D\uDCB0",
                    backgroundColor = Color.White
                )
            },
            title = if (account.name.isEmpty()) stringResource(ru.fav.moneytrace.ui.R.string.account) else account.name,
            trailingTitle = account.balance,
            trailingIcon = {
                MTIcon(
                    painter = painterResource(ru.fav.moneytrace.ui.R.drawable.ic_more),
                    contentDescription = stringResource(ru.fav.moneytrace.ui.R.string.more),
                )
            },
            onClick = { },
            height = Providers.spacing.xxl,
            backgroundColor = Providers.color.secondaryContainer
        )

        HorizontalDivider()

        MTListItem(
            title = stringResource(R.string.valute),
            trailingTitle = account.currency,
            trailingIcon = {
                MTIcon(
                    painter = painterResource(ru.fav.moneytrace.ui.R.drawable.ic_more),
                    contentDescription = stringResource(ru.fav.moneytrace.ui.R.string.more),
                )
            },
            onClick = { },
            height = Providers.spacing.xxl,
            backgroundColor = Providers.color.secondaryContainer
        )
    }
}