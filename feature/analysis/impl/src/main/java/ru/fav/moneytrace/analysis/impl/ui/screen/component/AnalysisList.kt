package ru.fav.moneytrace.analysis.impl.ui.screen.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.fav.moneytrace.analysis.impl.ui.model.CategoryAnalysisUIModel
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.component.MTEmojiIcon
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTListItem

@Composable
fun AnalysisList(
    categories: List<CategoryAnalysisUIModel>,
    onCategoryClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(
            count = categories.size,
            key = { index -> categories[index].id }
        ) { index ->
            val category = categories[index]
            MTListItem(
                leadingIcon = { 
                    MTEmojiIcon(
                        emoji = category.emoji
                    ) 
                },
                title = category.name,
                trailingTitle = category.percentage,
                trailingSubtitle = category.amount,
                trailingIcon = {
                    MTIcon(
                        painter = painterResource(R.drawable.ic_more),
                        contentDescription = stringResource(R.string.more),
                    )
                },
                onClick = { onCategoryClick(category.id) }
            )
            HorizontalDivider()
        }
    }
}