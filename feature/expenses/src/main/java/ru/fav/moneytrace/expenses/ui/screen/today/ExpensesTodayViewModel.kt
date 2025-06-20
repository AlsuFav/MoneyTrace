package ru.fav.moneytrace.expenses.ui.screen.today

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
import ru.fav.moneytrace.domain.provider.ResourceProvider
import ru.fav.moneytrace.expenses.domain.usecase.GetTodayExpensesUseCase
import ru.fav.moneytrace.domain.usecase.GetTransactionTotalSumUseCase
import ru.fav.moneytrace.util.result.FailureReason
import ru.fav.moneytrace.util.result.Result
import ru.fav.moneytrace.expenses.ui.mapper.ExpenseUIMapper
import ru.fav.moneytrace.expenses.ui.screen.today.state.ExpensesTodayEffect
import ru.fav.moneytrace.expenses.ui.screen.today.state.ExpensesTodayEvent
import ru.fav.moneytrace.expenses.ui.screen.today.state.ExpensesTodayState
import ru.fav.moneytrace.ui.R
import javax.inject.Inject

@HiltViewModel
class ExpensesTodayViewModel @Inject constructor(
    private val getTodayExpensesUseCase: GetTodayExpensesUseCase,
    private val getTransactionTotalSumUseCase: GetTransactionTotalSumUseCase,
    private val expenseUIMapper: ExpenseUIMapper,
    private val resourceProvider: ResourceProvider
): ViewModel() {

    private val _state = MutableStateFlow(ExpensesTodayState())
    val state: StateFlow<ExpensesTodayState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ExpensesTodayEffect>()
    val effect: SharedFlow<ExpensesTodayEffect> = _effect.asSharedFlow()

    fun reduce(event: ExpensesTodayEvent) {
        when (event) {
            ExpensesTodayEvent.HideErrorDialog ->
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
            ExpensesTodayEvent.Retry -> {
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
                loadTodayExpenses()
            }
        }
    }

    init {
        loadTodayExpenses()
    }

    private fun loadTodayExpenses() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(1000)

            when (val result = getTodayExpensesUseCase()) {
                is Result.Success -> {
                    val expenses = result.data
                    val totalSum = getTransactionTotalSumUseCase(expenses)
                    _state.update {
                        it.copy(
                            isLoading = false,
                            expenses = expenseUIMapper.mapList(expenses),
                            total = expenseUIMapper.mapTotal(totalSum, expenses.firstOrNull()?.account?.currency ?: "RUB")
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
                            expenses = emptyList(),
                            showErrorDialog = message
                        )
                    }
                }
            }
        }
    }

    private suspend fun handleFailure(failureReason: FailureReason) {
        val message = when (failureReason) {
            is FailureReason.Unauthorized -> resourceProvider.getString(R.string.failure_unauthorized)
            is FailureReason.Server -> resourceProvider.getString(R.string.failure_server)
            is FailureReason.BadRequest -> resourceProvider.getString(R.string.failure_bad_request)
            else -> resourceProvider.getString(R.string.failure_unknown)
        }
        _state.update {
            it.copy(
                isLoading = false,
                expenses = emptyList(),
                showErrorDialog = message
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}