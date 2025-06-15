package ru.fav.moneytrace.stats.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.fav.moneytrace.base.component.MTEmojiIcon
import ru.fav.moneytrace.base.component.MTIcon
import ru.fav.moneytrace.base.component.MTIconButton
import ru.fav.moneytrace.base.component.MTListItem
import ru.fav.moneytrace.base.component.MTTextField
import ru.fav.moneytrace.base.state.TopAppBarState
import ru.fav.moneytrace.stats.R

@Composable
fun StatsScreen(
    topAppBarSetter: (TopAppBarState) -> Unit,
    viewModel: StatsViewModel = hiltViewModel()
    ) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val topAppBarState = TopAppBarState(
        isVisible = true,
        title = stringResource(R.string.my_stats),
    )

    LaunchedEffect(topAppBarState) {
        topAppBarSetter(topAppBarState)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        MTTextField(
            value = state.input,
            onValueChange = { newInput ->
                viewModel.reduce(StatsEvent.OnInputChanged(newInput))
            },
            placeholder = stringResource(R.string.find_stat),
            trailingIcon = {
                MTIconButton(
                    onClick = { viewModel.reduce(StatsEvent.OnSearch) }
                ) {
                    MTIcon(
                        painter = painterResource(ru.fav.moneytrace.base.R.drawable.ic_search),
                        contentDescription = stringResource(ru.fav.moneytrace.base.R.string.history),
                    )
                }
            }
        )

        HorizontalDivider()

        LazyColumn {
            items(
                count = state.stats.size,
                key = { index -> state.stats[index].categoryId }
            ) { index ->
                val stat = state.stats[index]
                MTListItem(
                    leadingIcon = {
                        MTEmojiIcon(
                            emoji = stat.emoji,
                        )
                    },
                    title = stat.categoryName,
                    onClick = { }
                )
                HorizontalDivider()
            }
        }
    }
}