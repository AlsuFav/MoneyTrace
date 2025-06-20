package ru.fav.moneytrace.expenses.ui.screen.history

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
import ru.fav.moneytrace.domain.usecase.GetTransactionTotalSumUseCase
import ru.fav.moneytrace.util.result.FailureReason
import ru.fav.moneytrace.util.result.Result
import ru.fav.moneytrace.expenses.domain.usecase.GetExpensesByPeriodUseCase
import ru.fav.moneytrace.expenses.ui.screen.history.state.ExpensesHistoryEffect
import ru.fav.moneytrace.expenses.ui.screen.history.state.ExpensesHistoryEvent
import ru.fav.moneytrace.expenses.ui.screen.history.state.ExpensesHistoryState
import ru.fav.moneytrace.expenses.ui.mapper.ExpenseUIMapper
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.util.DateHelper
import javax.inject.Inject

@HiltViewModel
class ExpensesHistoryViewModel @Inject constructor(
    private val getExpensesByPeriodUseCase: GetExpensesByPeriodUseCase,
    private val getTransactionTotalSumUseCase: GetTransactionTotalSumUseCase,
    private val expenseUIMapper: ExpenseUIMapper,
    private val resourceProvider: ResourceProvider
): ViewModel() {

    private val _state = MutableStateFlow(ExpensesHistoryState())
    val state: StateFlow<ExpensesHistoryState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ExpensesHistoryEffect>()
    val effect: SharedFlow<ExpensesHistoryEffect> = _effect.asSharedFlow()

    fun reduce(event: ExpensesHistoryEvent) {
        when (event) {
            is ExpensesHistoryEvent.ShowStartDatePicker -> {
                _state.update { it.copy(showStartDatePicker = true) }
            }
            is ExpensesHistoryEvent.ShowEndDatePicker -> {
                _state.update { it.copy(showEndDatePicker = true) }
            }
            is ExpensesHistoryEvent.HideDatePicker -> {
                _state.update {
                    it.copy(
                        showStartDatePicker = false,
                        showEndDatePicker = false
                    )
                }
            }
            is ExpensesHistoryEvent.OnStartDateSelected -> {
                val formattedDate = DateHelper.formatDateForDisplay(event.date) ?: return
                _state.update {
                    it.copy(
                        startDate = formattedDate,
                        showStartDatePicker = false
                    )
                }
                loadExpensesByPeriod()
            }
            is ExpensesHistoryEvent.OnEndDateSelected -> {
                val formattedDate = DateHelper.formatDateForDisplay(event.date) ?: return
                _state.update {
                    it.copy(
                        endDate = formattedDate,
                        showEndDatePicker = false
                    )
                }
                loadExpensesByPeriod()
            }

            ExpensesHistoryEvent.HideErrorDialog ->
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
            ExpensesHistoryEvent.Retry -> {
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
                loadExpensesByPeriod()
            }
        }
    }

    init {
        loadExpensesByPeriod()
    }

    private fun loadExpensesByPeriod() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(1000)

            when (val result = getExpensesByPeriodUseCase(
                startDate = DateHelper.parseDisplayDate(state.value.startDate),
                endDate = DateHelper.parseDisplayDate(state.value.endDate),
            )) {
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

    private fun handleFailure(failureReason: FailureReason) {
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