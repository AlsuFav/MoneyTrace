package ru.fav.moneytrace.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.theme.Providers

/**
 * Компонент для выбора периода дат (начальная и конечная дата).
 *
 * @param startDate Отформатированная начальная дата
 * @param endDate Отформатированная конечная дата
 * @param onStartDateClick Callback при нажатии на начальную дату
 * @param onEndDateClick Callback при нажатии на конечную дату
 * @param modifier Модификатор для настройки внешнего вида
 */

@Composable
fun MTPeriodItem(
    startDate: String,
    endDate: String,
    onStartDateClick: () -> Unit,
    onEndDateClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Providers.color.secondaryContainer,
    mainColor: Color = Providers.color.onSurface,
    dateHasAccent: Boolean = false,
    accentColor: Color = Providers.color.secondaryContainer
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(containerColor)
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = onStartDateClick)
                .fillMaxWidth()
                .padding(PaddingValues(horizontal = Providers.spacing.m))
                .height(Providers.spacing.xxl),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MTText(
                text = stringResource(R.string.start),
                color = mainColor,
            )

            Spacer(modifier = Modifier.weight(1f))

            DateChip(
                text = startDate,
                dateHasAccent = dateHasAccent,
                backgroundColor = accentColor,
            )
        }

        HorizontalDivider()

        Row(
            modifier = Modifier
                .clickable(onClick = onEndDateClick)
                .fillMaxWidth()
                .padding(PaddingValues(horizontal = Providers.spacing.m))
                .height(Providers.spacing.xxl),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MTText(
                text = stringResource(R.string.end),
                color = mainColor,
            )

            Spacer(modifier = Modifier.weight(1f))

            DateChip(
                text = endDate,
                dateHasAccent = dateHasAccent,
                backgroundColor = accentColor,
            )
        }
    }
}

@Composable
private fun DateChip(
    text: String,
    modifier: Modifier = Modifier,
    dateHasAccent: Boolean = false,
    backgroundColor: Color = Providers.color.primary,
    contentColor: Color = Providers.color.onSurface
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(percent = 50)
            )
            .padding(PaddingValues(vertical = Providers.spacing.s, horizontal = Providers.spacing.m)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = contentColor,
            fontWeight = if (dateHasAccent) FontWeight.Bold else FontWeight.Normal,
        )
    }
}