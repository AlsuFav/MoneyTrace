package ru.fav.moneytrace.expenses.ui

import androidx.compose.animation.core.TransitionState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.fav.moneytrace.domain.usecase.GetAccountUseCase
import ru.fav.moneytrace.domain.usecase.GetTodayExpensesUseCase
import ru.fav.moneytrace.domain.usecase.GetTransactionTotalSumUseCase
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val getTodayExpensesUseCase: GetTodayExpensesUseCase,
    private val getTransactionTotalSumUseCase: GetTransactionTotalSumUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(ExpensesState())
    val state: StateFlow<ExpensesState> = _state.asStateFlow()

    fun reduce(event: ExpensesEvent) {
        when (event) {
            else -> {}
        }
    }

    init {
        loadTodayExpenses()
    }

    private fun loadTodayExpenses() {
        viewModelScope.launch {
            runCatching {
                val expenses = getTodayExpensesUseCase()
                val totalSum = getTransactionTotalSumUseCase(expenses)

                expenses to totalSum
            }.fold(
                onSuccess = { (expenses, totalSum) ->
                    _state.update {
                        it.copy(
                            expenses = expenses,
                            total = totalSum,
                            currency = expenses.firstOrNull()?.currency ?: "RUB"
                        )
                    }
                },
                onFailure = { }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}