package ru.fav.moneytrace.transaction.impl.ui.screen.create

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import ru.fav.moneytrace.account.api.usecase.GetAccountUseCase
import ru.fav.moneytrace.categories.api.usecase.GetCategoriesUseCase
import ru.fav.moneytrace.domain.provider.ResourceProvider
import ru.fav.moneytrace.transaction.api.model.TransactionType
import ru.fav.moneytrace.transaction.impl.domain.usecase.CreateTransactionUseCase
import ru.fav.moneytrace.transaction.impl.ui.mapper.AccountUIMapper
import ru.fav.moneytrace.transaction.impl.ui.mapper.CategoryUIMapper
import ru.fav.moneytrace.transaction.impl.ui.mapper.TransactionUIMapper
import ru.fav.moneytrace.transaction.impl.ui.model.AccountUIModel
import ru.fav.moneytrace.transaction.impl.ui.model.TransactionUIModel
import ru.fav.moneytrace.transaction.impl.ui.screen.create.state.TransactionCreateEffect
import ru.fav.moneytrace.transaction.impl.ui.screen.create.state.TransactionCreateEvent
import ru.fav.moneytrace.transaction.impl.ui.screen.create.state.TransactionCreateState
import ru.fav.moneytrace.transaction.impl.ui.screen.update.state.TransactionUpdateEffect
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.base.BaseViewModel
import ru.fav.moneytrace.util.DateHelper
import ru.fav.moneytrace.util.result.FailureReason
import javax.inject.Inject
import ru.fav.moneytrace.util.result.Result

@HiltViewModel
class TransactionCreateViewModel @Inject constructor(
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val categoryUIMapper: CategoryUIMapper,
    private val accountUIMapper: AccountUIMapper,
    private val resourceProvider: ResourceProvider
) : BaseViewModel<TransactionCreateState, TransactionCreateEvent, TransactionCreateEffect>() {

    override val _state = MutableStateFlow(TransactionCreateState())
    override val state: StateFlow<TransactionCreateState> = _state.asStateFlow()

    override val _effect = Channel<TransactionCreateEffect>(Channel.UNLIMITED)
    override val effect = _effect.receiveAsFlow()

    private var loadAccountJob: Job? = null
    private var loadCategoriesJob: Job? = null
    private var createTransactionJob: Job? = null

    override fun reduce(event: TransactionCreateEvent) {
        when (event) {
            is TransactionCreateEvent.HideErrorDialog ->
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
            is TransactionCreateEvent.LoadData -> {
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
                loadCategories(event.transactionType)
                loadAccount()
            }

            is TransactionCreateEvent.HideCategoryBottomSheet ->
                _state.update {
                    it.copy(
                        showBottomSheet = false
                    )
                }
            is TransactionCreateEvent.HideDatePicker -> {
                _state.update {
                    it.copy(
                        showDatePicker = false,
                    )
                }
            }
            is TransactionCreateEvent.HideTimePicker -> {
                _state.update {
                    it.copy(
                        showTimePicker = false,
                    )
                }
            }
            is TransactionCreateEvent.OnDateSelected -> {
                val formattedDate = DateHelper.formatDateForDisplay(event.date) ?: return
                _state.update {
                    it.copy(
                        transaction = it.transaction.copy(
                            date = formattedDate
                        )
                    )
                }
            }
            is TransactionCreateEvent.OnDoneClicked -> createTransaction()
            is TransactionCreateEvent.OnTimeSelected -> {
                val formattedTime = DateHelper.formatTimeForDisplay(event.time) ?: return
                _state.update {
                    it.copy(
                        transaction = it.transaction.copy(
                            time = formattedTime
                        )
                    )
                }
            }
            is TransactionCreateEvent.SelectCategory -> {
                _state.update {
                    it.copy(
                        transaction = it.transaction.copy(
                            category = event.category
                        )
                    )
                }
            }
            is TransactionCreateEvent.ShowCategoryBottomSheet ->
                _state.update {
                    it.copy(
                        showBottomSheet = true
                    )
                }
            is TransactionCreateEvent.ShowDatePicker -> {
                _state.update { it.copy(showDatePicker = true) }
            }
            is TransactionCreateEvent.ShowTimePicker -> {
                _state.update { it.copy(showTimePicker = true) }
            }
            is TransactionCreateEvent.UpdateAmount -> _state.update {
                it.copy(
                    transaction = state.value.transaction.copy(
                        amount = event.amount
                    )
                )
            }
            is TransactionCreateEvent.UpdateComment -> _state.update {
                it.copy(
                    transaction = state.value.transaction.copy(
                        comment = event.comment
                    )
                )
            }
        }
    }

    private fun createTransaction() {
        createTransactionJob?.cancel()

        validateInputs()?.let { errorMessage ->
            sendEffect(TransactionCreateEffect.ShowToast(errorMessage))
            return
        }

        createTransactionJob = launchTask {
            _state.update { it.copy(isLoading = true) }
            delay(1000)

            when (val result = createTransactionUseCase(
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
                    sendEffect(TransactionCreateEffect.ShowToast(
                        resourceProvider.getString(
                            ru.fav.moneytrace.transaction.impl.R.string.transaction_created_successfully
                        )))
                    sendEffect(TransactionCreateEffect.TransactionCreated)
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

    private fun validateInputs() : String? {
        return when {
            state.value.transaction.account.id == 0
                -> resourceProvider.getString(ru.fav.moneytrace.transaction.impl.R.string.error_empty_account)
            state.value.transaction.category.id == 0
                -> resourceProvider.getString(ru.fav.moneytrace.transaction.impl.R.string.error_empty_category)
            else -> null
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
                            categories = categoryUIMapper.mapList(result.data),
                            showErrorDialog = if (result.cached) resourceProvider.getString(R.string.failure_network) else null
                        )
                    }
                }
                is Result.Failure -> {
                    handleFailure(result.reason)
                }
            }
        }
    }

    private fun loadAccount() {
        loadAccountJob?.cancel()

        loadAccountJob = launchTask {
            _state.update { it.copy(isLoading = true) }
            delay(1000)

            when (val result = getAccountUseCase()) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            transaction = it.transaction.copy(
                                account = accountUIMapper.map(result.data)
                            ),
                            showErrorDialog = if (result.cached) resourceProvider.getString(R.string.failure_network) else null
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