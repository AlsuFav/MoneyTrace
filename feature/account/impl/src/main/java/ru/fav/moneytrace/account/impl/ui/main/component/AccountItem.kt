package ru.fav.moneytrace.account.impl.ui.main.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.fav.moneytrace.account.impl.ui.model.AccountUIModel
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTEmojiIcon
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTListItem
import ru.fav.moneytrace.ui.theme.Providers
import ru.fav.moneytrace.ui.util.extension.formatAmount
import ru.fav.moneytrace.ui.util.extension.toCurrencySymbol

/**
 * Компонент для отображения информации о счете пользователя.
 *
 * Показывает название счета, баланс и валюту в виде двух связанных элементов списка.
 *
 * @param account Модель счета для отображения
 */

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
            title = if (account.name.isEmpty()) stringResource(R.string.account) else account.name,
            trailingTitle = "${account.balance} ${account.currency.symbol}",
            trailingIcon = {
                MTIcon(
                    painter = painterResource(R.drawable.ic_more),
                    contentDescription = stringResource(R.string.more),
                )
            },
            onClick = { },
            height = Providers.spacing.xxl,
            backgroundColor = Providers.color.secondaryContainer
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
            onClick = { },
            height = Providers.spacing.xxl,
            backgroundColor = Providers.color.secondaryContainer
        )
    }
}