package ru.fav.moneytrace.categories.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.fav.moneytrace.categories.ui.state.CategoriesEffect
import ru.fav.moneytrace.categories.ui.state.CategoriesEvent
import ru.fav.moneytrace.categories.ui.state.CategoriesState
import ru.fav.moneytrace.domain.provider.ResourceProvider
import ru.fav.moneytrace.categories.domain.usecase.GetExpenseCategoriesUseCase
import ru.fav.moneytrace.categories.ui.mapper.CategoryDetailsUIMapper
import ru.fav.moneytrace.util.result.FailureReason
import ru.fav.moneytrace.util.result.Result
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getExpenseCategoriesUseCase: GetExpenseCategoriesUseCase,
    private val categoryDetailsUIMapper: CategoryDetailsUIMapper,
    private val resourceProvider: ResourceProvider
) : BaseViewModel<CategoriesState, CategoriesEvent, CategoriesEffect>() {

    override val _state = MutableStateFlow(CategoriesState())
    override val state: StateFlow<CategoriesState> = _state.asStateFlow()

    override val _effect = MutableSharedFlow<CategoriesEffect>()
    override val effect: SharedFlow<CategoriesEffect> = _effect.asSharedFlow()

    private var loadCategoriesJob: Job? = null

    override fun reduce(event: CategoriesEvent) {
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
            CategoriesEvent.LoadCategories -> {
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
                loadExpenseCategories()
            }
        }
    }

    private fun loadExpenseCategories() {
        loadCategoriesJob?.cancel()

        loadCategoriesJob = launchTask {
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
                is Result.Failure -> {
                    handleFailure(result.reason)
                }
            }
        }
    }

    private fun handleFailure(failureReason: FailureReason) {
        val message = when (failureReason) {
            is FailureReason.Network -> resourceProvider.getString(R.string.failure_network)
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
}