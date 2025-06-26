package ru.fav.moneytrace.expenses.ui.screen.today

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
import ru.fav.moneytrace.domain.provider.ResourceProvider
import ru.fav.moneytrace.expenses.domain.usecase.GetTodayExpensesUseCase
import ru.fav.moneytrace.util.result.FailureReason
import ru.fav.moneytrace.util.result.Result
import ru.fav.moneytrace.expenses.ui.mapper.ExpenseUIMapper
import ru.fav.moneytrace.expenses.ui.screen.today.state.ExpensesTodayEffect
import ru.fav.moneytrace.expenses.ui.screen.today.state.ExpensesTodayEvent
import ru.fav.moneytrace.expenses.ui.screen.today.state.ExpensesTodayState
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ExpensesTodayViewModel @Inject constructor(
    private val getTodayExpensesUseCase: GetTodayExpensesUseCase,
    private val expenseUIMapper: ExpenseUIMapper,
    private val resourceProvider: ResourceProvider
) : BaseViewModel<ExpensesTodayState, ExpensesTodayEvent, ExpensesTodayEffect>() {

    override val _state = MutableStateFlow(ExpensesTodayState())
    override val state: StateFlow<ExpensesTodayState> = _state.asStateFlow()

    override val _effect = MutableSharedFlow<ExpensesTodayEffect>()
    override val effect: SharedFlow<ExpensesTodayEffect> = _effect.asSharedFlow()

    private var loadExpensesJob: Job? = null

    override fun reduce(event: ExpensesTodayEvent) {
        when (event) {
            ExpensesTodayEvent.HideErrorDialog ->
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
            ExpensesTodayEvent.LoadExpenses -> {
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
                loadTodayExpenses()
            }
        }
    }

    private fun loadTodayExpenses() {
        loadExpensesJob?.cancel()

        loadExpensesJob = launchTask {
            _state.update { it.copy(isLoading = true) }
            delay(1000)

            when (val result = getTodayExpensesUseCase()) {
                is Result.Success -> {
                    val expenses = result.data
                    val totalSum = expenses.sumOf { it.amount }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            expenses = expenseUIMapper.mapList(expenses),
                            total = expenseUIMapper.mapTotal(totalSum, expenses.firstOrNull()?.account?.currency ?: "RUB")
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
}