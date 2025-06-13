package ru.fav.moneytrace.income.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.fav.moneytrace.domain.usecase.GetTodayIncomeUseCase
import ru.fav.moneytrace.domain.usecase.GetTransactionTotalSumUseCase
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val getTodayIncomeUseCase: GetTodayIncomeUseCase,
    private val getTransactionTotalSumUseCase: GetTransactionTotalSumUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(IncomeState())
    val state: StateFlow<IncomeState> = _state.asStateFlow()

    fun reduce(event: IncomeEvent) {
        when (event) {
            else -> {}
        }
    }

    init {
        loadTodayIncome()
    }

    private fun loadTodayIncome() {
        viewModelScope.launch {
            runCatching {
                val income = getTodayIncomeUseCase()
                val totalSum = getTransactionTotalSumUseCase(income)

                income to totalSum
            }.fold(
                onSuccess = { (income, totalSum) ->
                    _state.update {
                        it.copy(
                            income = income,
                            total = totalSum,
                            currency = income.firstOrNull()?.currency ?: "RUB"
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