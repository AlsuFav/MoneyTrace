package ru.fav.moneytrace.categories.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.fav.moneytrace.categories.R
import ru.fav.moneytrace.categories.ui.component.CategoriesList
import ru.fav.moneytrace.categories.ui.component.CategoriesShimmerList
import ru.fav.moneytrace.categories.ui.state.CategoriesEvent
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTIconButton
import ru.fav.moneytrace.ui.component.MTTextField
import ru.fav.moneytrace.ui.component.MTCenterAlignedTopAppBar
import ru.fav.moneytrace.ui.component.MTErrorDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.reduce(CategoriesEvent.LoadCategories)
    }

    DisposableEffect(viewModel) {
        onDispose {
            viewModel.cancelAllTasks()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MTCenterAlignedTopAppBar(
            title = stringResource(R.string.my_stats)
        )

        Box(
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                MTTextField(
                    value = state.input,
                    onValueChange = { newInput ->
                        viewModel.reduce(CategoriesEvent.OnInputChanged(newInput))
                    },
                    placeholder = stringResource(R.string.find_stat),
                    trailingIcon = {
                        MTIconButton(
                            onClick = { viewModel.reduce(CategoriesEvent.OnSearch) }
                        ) {
                            MTIcon(
                                painter = painterResource(ru.fav.moneytrace.ui.R.drawable.ic_search),
                                contentDescription = stringResource(ru.fav.moneytrace.ui.R.string.search),
                            )
                        }
                    },
                )

                HorizontalDivider()

                when {
                    state.isLoading -> {
                        CategoriesShimmerList()
                    }

                    else -> {
                        CategoriesList(
                            categories = state.categories,
                            onCategoryClick = { category ->
                            }
                        )
                    }
                }
            }
        }
    }

    state.showErrorDialog?.let { message ->
        MTErrorDialog(
            message = message,
            confirmButtonText = stringResource(ru.fav.moneytrace.ui.R.string.repeat),
            dismissButtonText = stringResource(ru.fav.moneytrace.ui.R.string.exit),
            onConfirm = {
                viewModel.reduce(CategoriesEvent.LoadCategories)
            },
            onDismiss = {
                viewModel.reduce(CategoriesEvent.HideErrorDialog)
            }
        )
    }
}
