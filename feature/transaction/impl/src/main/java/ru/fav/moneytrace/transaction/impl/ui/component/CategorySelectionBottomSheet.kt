package ru.fav.moneytrace.transaction.impl.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.fav.moneytrace.transaction.impl.ui.model.CategoryUIModel
import ru.fav.moneytrace.ui.component.MTEmojiIcon
import ru.fav.moneytrace.ui.component.MTListItem
import ru.fav.moneytrace.ui.theme.Providers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelectionBottomSheet(
    categories: List<CategoryUIModel>,
    onCategorySelected: (CategoryUIModel) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = rememberModalBottomSheetState(),
        containerColor = Providers.color.surface,
    ) {
        Column {
            LazyColumn(
                modifier = modifier
                    .weight(1f, fill = false)
                    .heightIn(max = 500.dp)
            ) {
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
                        onClick = { onCategorySelected(category) }
                    )
                    HorizontalDivider()
                }
            }

            HorizontalDivider()
        }
    }
}