package ru.fav.moneytrace.stats.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.fav.moneytrace.ui.component.MTEmojiIcon
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTIconButton
import ru.fav.moneytrace.ui.component.MTListItem
import ru.fav.moneytrace.ui.component.MTTextField
import ru.fav.moneytrace.stats.R
import ru.fav.moneytrace.ui.component.MTCenterAlignedTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    viewModel: StatsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            MTCenterAlignedTopAppBar(
                title = stringResource(R.string.my_stats)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                            painter = painterResource(ru.fav.moneytrace.ui.R.drawable.ic_search),
                            contentDescription = stringResource(ru.fav.moneytrace.ui.R.string.history),
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
}