package ru.fav.moneytrace.transaction.impl.ui.screen.update

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import ru.fav.moneytrace.categories.api.usecase.GetCategoriesUseCase
import ru.fav.moneytrace.domain.provider.ResourceProvider
import ru.fav.moneytrace.transaction.api.model.TransactionType
import ru.fav.moneytrace.transaction.impl.domain.usecase.DeleteTransactionUseCase
import ru.fav.moneytrace.transaction.impl.domain.usecase.GetTransactionByIdUseCase
import ru.fav.moneytrace.transaction.impl.domain.usecase.UpdateTransactionUseCase
import ru.fav.moneytrace.transaction.impl.ui.mapper.CategoryUIMapper
import ru.fav.moneytrace.transaction.impl.ui.mapper.TransactionUIMapper
import ru.fav.moneytrace.transaction.impl.ui.model.TransactionUIModel
import ru.fav.moneytrace.transaction.impl.ui.screen.update.state.TransactionUpdateEffect
import ru.fav.moneytrace.transaction.impl.ui.screen.update.state.TransactionUpdateEvent
import ru.fav.moneytrace.transaction.impl.ui.screen.update.state.TransactionUpdateState
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.base.BaseViewModel
import ru.fav.moneytrace.util.DateHelper
import ru.fav.moneytrace.util.result.FailureReason
import javax.inject.Inject
import ru.fav.moneytrace.util.result.Result

@HiltViewModel
class TransactionUpdateViewModel @Inject constructor(
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val categoryUIMapper: CategoryUIMapper,
    private val transactionUIMapper: TransactionUIMapper,
    private val resourceProvider: ResourceProvider
) : BaseViewModel<TransactionUpdateState, TransactionUpdateEvent, TransactionUpdateEffect>() {

    override val _state = MutableStateFlow(TransactionUpdateState())
    override val state: StateFlow<TransactionUpdateState> = _state.asStateFlow()

    override val _effect = Channel<TransactionUpdateEffect>(Channel.UNLIMITED)
    override val effect = _effect.receiveAsFlow()

    private var loadTransactionJob: Job? = null
    private var loadCategoriesJob: Job? = null
    private var deleteTransactionJob: Job? = null
    private var updateTransactionJob: Job? = null

    override fun reduce(event: TransactionUpdateEvent) {
        when (event) {
            is TransactionUpdateEvent.HideErrorDialog ->
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
            is TransactionUpdateEvent.LoadData -> {
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
                loadTransaction(event.transactionId)
                loadCategories(event.transactionType)
            }

            is TransactionUpdateEvent.HideCategoryBottomSheet ->
                _state.update {
                    it.copy(
                        showBottomSheet = false
                    )
                }
            is TransactionUpdateEvent.HideDatePicker -> {
                _state.update {
                    it.copy(
                        showDatePicker = false,
                    )
                }
            }
            is TransactionUpdateEvent.HideTimePicker -> {
                _state.update {
                    it.copy(
                        showTimePicker = false,
                    )
                }
            }
            is TransactionUpdateEvent.OnDateSelected -> {
                val formattedDate = DateHelper.formatDateForDisplay(event.date) ?: return
                _state.update {
                    it.copy(
                        transaction = it.transaction.copy(
                            date = formattedDate
                        )
                    )
                }
            }
            is TransactionUpdateEvent.OnDoneClicked -> updateTransaction()
            is TransactionUpdateEvent.OnTimeSelected -> {
                val formattedTime = DateHelper.formatTimeForDisplay(event.time) ?: return
                _state.update {
                    it.copy(
                        transaction = it.transaction.copy(
                            time = formattedTime
                        )
                    )
                }
            }
            is TransactionUpdateEvent.SelectCategory -> {
                _state.update {
                    it.copy(
                        transaction = it.transaction.copy(
                            category = event.category
                        )
                    )
                }
            }
            is TransactionUpdateEvent.ShowCategoryBottomSheet ->
                _state.update {
                    it.copy(
                        showBottomSheet = true
                    )
                }
            is TransactionUpdateEvent.ShowDatePicker -> {
                _state.update { it.copy(showDatePicker = true) }
            }
            is TransactionUpdateEvent.ShowTimePicker -> {
                _state.update { it.copy(showTimePicker = true) }
            }
            is TransactionUpdateEvent.UpdateAmount -> _state.update {
                it.copy(
                    transaction = state.value.transaction.copy(
                        amount = event.amount
                    )
                )
            }
            is TransactionUpdateEvent.UpdateComment -> _state.update {
                it.copy(
                    transaction = state.value.transaction.copy(
                        comment = event.comment
                    )
                )
            }

            is TransactionUpdateEvent.OnDeleteClicked -> deleteTransaction(event.transactionId)
        }
    }

    private fun loadTransaction(id: Int) {
        loadTransactionJob?.cancel()

        loadTransactionJob = launchTask {
            _state.update { it.copy(isLoading = true) }
            delay(1000)

            when (val result = getTransactionByIdUseCase(id)) {
                is Result.Success -> {
                    val transaction = result.data
                    _state.update {
                        it.copy(
                            isLoading = false,
                            transaction = transactionUIMapper.map(transaction),
                        )
                    }
                }
                is Result.Failure -> {
                    handleFailure(result.reason)
                }
            }
        }
    }

    private fun updateTransaction() {
        updateTransactionJob?.cancel()

        updateTransactionJob = launchTask {
            _state.update { it.copy(isLoading = true) }
            delay(1000)

            when (val result = updateTransactionUseCase(
                id = state.value.transaction.id,
                accountId = state.value.transaction.account.id,
                categoryId = state.value.transaction.category.id,
                amount = state.value.transaction.amount.let {
                    if (it.isEmpty()) "0" else it
                },
                transactionDate = DateHelper.parseDisplayDateAndTime(
                    state.value.transaction.date,
                    state.value.transaction.time,
                ),
                comment = state.value.transaction.comment,
            )) {
                is Result.Success -> {
                    sendEffect(TransactionUpdateEffect.ShowToast(
                        resourceProvider.getString(
                            ru.fav.moneytrace.transaction.impl.R.string.transaction_updated_successfully
                        )))
                    sendEffect(TransactionUpdateEffect.TransactionUpdated)
                    _state.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                }
                is Result.Failure -> {
                    handleFailure(result.reason)
                }
            }
        }
    }

    private fun loadCategories(transactionType: TransactionType) {
        loadCategoriesJob?.cancel()

        loadCategoriesJob = launchTask {
            _state.update { it.copy(isLoading = true) }
            delay(1000)

            when (val result = getCategoriesUseCase(transactionType)) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            categories = categoryUIMapper.mapList(result.data)
                        )
                    }
                }
                is Result.Failure -> {
                    handleFailure(result.reason)
                }
            }
        }
    }
    private fun deleteTransaction(id: Int) {
        deleteTransactionJob?.cancel()

        deleteTransactionJob = launchTask {
            _state.update { it.copy(isLoading = true) }
            delay(1000)

            when (val result = deleteTransactionUseCase(id)) {
                is Result.Success -> {
                    sendEffect(TransactionUpdateEffect.ShowToast(
                        resourceProvider.getString(
                            ru.fav.moneytrace.transaction.impl.R.string.transaction_deleted_successfully
                        )))
                    sendEffect(TransactionUpdateEffect.TransactionDeleted)
                    _state.update {
                        it.copy(
                            isLoading = false,
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
                transaction = TransactionUIModel(),
                showErrorDialog = message
            )
        }
    }
}