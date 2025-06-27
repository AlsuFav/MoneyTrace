package ru.fav.moneytrace.categories.impl.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.fav.moneytrace.categories.impl.R
import ru.fav.moneytrace.categories.impl.ui.component.CategoriesList
import ru.fav.moneytrace.categories.impl.ui.component.CategoriesShimmerList
import ru.fav.moneytrace.categories.impl.ui.state.CategoriesEvent
import ru.fav.moneytrace.ui.component.MTIcon
import ru.fav.moneytrace.ui.component.MTIconButton
import ru.fav.moneytrace.ui.component.MTTextField
import ru.fav.moneytrace.ui.component.MTCenterAlignedTopAppBar
import ru.fav.moneytrace.ui.component.MTErrorDialog

/**
 * Экран категорий с поиском и фильтрацией.
 *
 * Отображает список категорий расходов с возможностью поиска по названию.
 * Поддерживает состояния загрузки и обработки ошибок.
 *
 * @param viewModel ViewModel для управления состоянием экрана
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.reduce(CategoriesEvent.LoadCategories)
    }

    DisposableEffect(viewModel) {
        onDispose {
            viewModel.cancelAllTasks()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        MTCenterAlignedTopAppBar(
            title = stringResource(R.string.my_categories)
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
                    placeholder = stringResource(R.string.find_category),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            viewModel.reduce(CategoriesEvent.OnSearch)
                            focusManager.clearFocus()
                        }
                    ),
                    trailingIcon = {
                        MTIconButton(
                            onClick = {
                                viewModel.reduce(CategoriesEvent.OnSearch)
                                focusManager.clearFocus()
                            }
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
                                focusManager.clearFocus()
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
