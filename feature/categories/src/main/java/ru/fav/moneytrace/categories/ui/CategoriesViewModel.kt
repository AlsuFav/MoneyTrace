package ru.fav.moneytrace.categories.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.fav.moneytrace.categories.ui.state.CategoriesEffect
import ru.fav.moneytrace.categories.ui.state.CategoriesEvent
import ru.fav.moneytrace.categories.ui.state.CategoriesState
import ru.fav.moneytrace.domain.provider.ResourceProvider
import ru.fav.moneytrace.categories.domain.usecase.GetExpenseCategoriesUseCase
import ru.fav.moneytrace.categories.ui.mapper.CategoryDetailsUIMapper
import ru.fav.moneytrace.categories.ui.model.CategoryDetailsUIModel
import ru.fav.moneytrace.util.result.FailureReason
import ru.fav.moneytrace.util.result.Result
import ru.fav.moneytrace.ui.R
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getExpenseCategoriesUseCase: GetExpenseCategoriesUseCase,
    private val categoryDetailsUIMapper: CategoryDetailsUIMapper,
    private val resourceProvider: ResourceProvider
): ViewModel() {

    private val _state = MutableStateFlow(CategoriesState())
    val state: StateFlow<CategoriesState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CategoriesEffect>()
    val effect: SharedFlow<CategoriesEffect> = _effect.asSharedFlow()

    fun reduce(event: CategoriesEvent) {
        when (event) {
            is CategoriesEvent.OnInputChanged -> {
                _state.update {
                    it.copy(
                        input = event.input,
                    )
                }
                loadExpenseCategories()
            }
            is CategoriesEvent.OnSearch -> {
                loadExpenseCategories()
            }

            CategoriesEvent.HideErrorDialog ->
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
            CategoriesEvent.Retry -> {
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
                loadExpenseCategories()
            }
        }
    }

    init {
        loadExpenseCategories()
    }

    private fun loadExpenseCategories() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(1000)

            when (val result = getExpenseCategoriesUseCase(query = state.value.input)) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            categories = categoryDetailsUIMapper.mapList(result.data)
                        )
                    }
                }
                is Result.HttpError -> {
                    handleFailure(result.reason)
                }
                is Result.NetworkError -> {
                    val message = resourceProvider.getString(R.string.failure_network)
                    _state.update {
                        it.copy(
                            isLoading = false,
                            categories = emptyList(),
                            showErrorDialog = message
                        )
                    }
                }
            }
        }
    }

    private fun handleFailure(failureReason: FailureReason) {
        val message = when (failureReason) {
            is FailureReason.Unauthorized -> resourceProvider.getString(R.string.failure_unauthorized)
            is FailureReason.Server -> resourceProvider.getString(R.string.failure_server)
            is FailureReason.NotFound -> resourceProvider.getString(R.string.failure_not_found)
            else -> resourceProvider.getString(R.string.failure_unknown)
        }
        _state.update {
            it.copy(
                isLoading = false,
                categories = emptyList(),
                showErrorDialog = message
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}