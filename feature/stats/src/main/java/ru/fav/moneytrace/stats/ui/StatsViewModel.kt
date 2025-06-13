package ru.fav.moneytrace.stats.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.fav.moneytrace.domain.usecase.GetExpenseStatsUseCase
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val getExpenseStatsUseCase: GetExpenseStatsUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(StatsState())
    val state: StateFlow<StatsState> = _state.asStateFlow()

    fun reduce(event: StatsEvent) {
        when (event) {
            is StatsEvent.OnInputChanged -> {
                _state.update {
                    it.copy(
                        input = event.input,
                    )
                }
                loadExpenseStats()
            }
            is StatsEvent.OnSearch -> {
                loadExpenseStats()
            }
        }
    }

    init {
        loadExpenseStats()
    }

    private fun loadExpenseStats() {
        viewModelScope.launch {
            runCatching {
                getExpenseStatsUseCase(_state.value.input)
            }.fold(
                onSuccess = {
                        expenseStats ->
                    _state.update {
                        it.copy(
                            stats = expenseStats,
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