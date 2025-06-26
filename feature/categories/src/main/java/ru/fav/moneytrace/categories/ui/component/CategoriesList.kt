package ru.fav.moneytrace.categories.ui.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.fav.moneytrace.categories.ui.model.CategoryDetailsUIModel
import ru.fav.moneytrace.ui.component.MTEmojiIcon
import ru.fav.moneytrace.ui.component.MTListItem

@Composable
fun CategoriesList(
    categories: List<CategoryDetailsUIModel>,
    onCategoryClick: (CategoryDetailsUIModel) -> Unit,
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
                        emoji = category.emoji,
                    )
                },
                title = category.name,
                onClick = { onCategoryClick(category) }
            )
            HorizontalDivider()
        }
    }
}