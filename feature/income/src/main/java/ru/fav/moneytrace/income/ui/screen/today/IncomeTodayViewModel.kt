package ru.fav.moneytrace.income.ui.screen.today

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
import ru.fav.moneytrace.income.domain.usecase.GetTodayIncomeUseCase
import ru.fav.moneytrace.income.ui.mapper.IncomeUIMapper
import ru.fav.moneytrace.income.ui.screen.today.state.IncomeTodayEffect
import ru.fav.moneytrace.income.ui.screen.today.state.IncomeTodayEvent
import ru.fav.moneytrace.income.ui.screen.today.state.IncomeTodayState
import ru.fav.moneytrace.ui.R
import javax.inject.Inject

@HiltViewModel
class IncomeTodayViewModel @Inject constructor(
    private val getTodayIncomeUseCase: GetTodayIncomeUseCase,
    private val getTransactionTotalSumUseCase: GetTransactionTotalSumUseCase,
    private val incomeUIMapper: IncomeUIMapper,
    private val resourceProvider: ResourceProvider
): ViewModel() {

    private val _state = MutableStateFlow(IncomeTodayState())
    val state: StateFlow<IncomeTodayState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<IncomeTodayEffect>()
    val effect: SharedFlow<IncomeTodayEffect> = _effect.asSharedFlow()

    fun reduce(event: IncomeTodayEvent) {
        when (event) {
            IncomeTodayEvent.HideErrorDialog ->
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
            IncomeTodayEvent.Retry -> {
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
                loadTodayIncome()
            }
        }
    }

    init {
        loadTodayIncome()
    }

    private fun loadTodayIncome() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(1000)

            when (val result = getTodayIncomeUseCase()) {
                is Result.Success -> {
                    val income = result.data
                    val totalSum = getTransactionTotalSumUseCase(income)
                    _state.update {
                        it.copy(
                            isLoading = false,
                            income = incomeUIMapper.mapList(income),
                            total = incomeUIMapper.mapTotal(totalSum, income.firstOrNull()?.account?.currency ?: "RUB"),
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
                            income = emptyList(),
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
                income = emptyList(),
                showErrorDialog = message
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}