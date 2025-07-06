package ru.fav.moneytrace.income.impl.ui.screen.history

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import ru.fav.moneytrace.domain.provider.ResourceProvider
import ru.fav.moneytrace.util.result.FailureReason
import ru.fav.moneytrace.util.result.Result
import ru.fav.moneytrace.income.impl.domain.usecase.GetIncomeByPeriodUseCase
import ru.fav.moneytrace.income.impl.ui.mapper.IncomeUIMapper
import ru.fav.moneytrace.income.impl.ui.screen.history.state.IncomeHistoryEffect
import ru.fav.moneytrace.income.impl.ui.screen.history.state.IncomeHistoryEvent
import ru.fav.moneytrace.income.impl.ui.screen.history.state.IncomeHistoryState
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.base.BaseViewModel
import ru.fav.moneytrace.util.DateHelper
import javax.inject.Inject

@HiltViewModel
class IncomeHistoryViewModel @Inject constructor(
    private val getIncomeByPeriodUseCase: GetIncomeByPeriodUseCase,
    private val incomeUIMapper: IncomeUIMapper,
    private val resourceProvider: ResourceProvider
) : BaseViewModel<IncomeHistoryState, IncomeHistoryEvent, IncomeHistoryEffect>() {

    override val _state = MutableStateFlow(IncomeHistoryState())
    override val state: StateFlow<IncomeHistoryState> = _state.asStateFlow()

    override val _effect = Channel<IncomeHistoryEffect>(Channel.UNLIMITED)
    override val effect = _effect.receiveAsFlow()

    private var loadIncomeJob: Job? = null

    override fun reduce(event: IncomeHistoryEvent) {
        when (event) {
            is IncomeHistoryEvent.ShowStartDatePicker -> {
                _state.update { it.copy(showStartDatePicker = true) }
            }
            is IncomeHistoryEvent.ShowEndDatePicker -> {
                _state.update { it.copy(showEndDatePicker = true) }
            }
            is IncomeHistoryEvent.HideDatePicker -> {
                _state.update {
                    it.copy(
                        showStartDatePicker = false,
                        showEndDatePicker = false
                    )
                }
            }
            is IncomeHistoryEvent.OnStartDateSelected -> {
                val formattedDate = DateHelper.formatDateForDisplay(event.date) ?: return
                _state.update {
                    it.copy(
                        startDate = formattedDate,
                        showStartDatePicker = false
                    )
                }
                loadIncomeByPeriod()
            }
            is IncomeHistoryEvent.OnEndDateSelected -> {
                val formattedDate = DateHelper.formatDateForDisplay(event.date) ?: return
                _state.update {
                    it.copy(
                        endDate = formattedDate,
                        showEndDatePicker = false
                    )
                }
                loadIncomeByPeriod()
            }
            IncomeHistoryEvent.HideErrorDialog ->
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
            IncomeHistoryEvent.LoadIncome -> {
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
                loadIncomeByPeriod()
            }
        }
    }

    private fun loadIncomeByPeriod() {
        loadIncomeJob?.cancel()

        loadIncomeJob = launchTask {
            _state.update { it.copy(isLoading = true) }
            delay(1000)

            when (val result = getIncomeByPeriodUseCase(
                startDate = DateHelper.parseDisplayDate(state.value.startDate),
                endDate = DateHelper.parseDisplayDate(state.value.endDate),
            )) {
                is Result.Success -> {
                    val income = result.data
                    val totalSum = income.sumOf { it.amount }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            income = incomeUIMapper.mapList(income),
                            total = incomeUIMapper.mapTotal(totalSum, income.firstOrNull()?.account?.currency ?: "RUB")
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
                income = emptyList(),
                showErrorDialog = message
            )
        }
    }
}