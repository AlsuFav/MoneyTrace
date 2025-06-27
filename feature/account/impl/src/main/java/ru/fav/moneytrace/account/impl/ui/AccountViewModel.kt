package ru.fav.moneytrace.account.impl.ui

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
import ru.fav.moneytrace.account.impl.ui.state.AccountEffect
import ru.fav.moneytrace.account.impl.ui.state.AccountEvent
import ru.fav.moneytrace.account.impl.ui.state.AccountState
import ru.fav.moneytrace.domain.provider.ResourceProvider
import ru.fav.moneytrace.account.impl.domain.usecase.GetAccountUseCase
import ru.fav.moneytrace.account.impl.ui.mapper.AccountUIMapper
import ru.fav.moneytrace.account.impl.ui.model.AccountUIModel
import ru.fav.moneytrace.util.result.FailureReason
import javax.inject.Inject
import ru.fav.moneytrace.util.result.Result
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.base.BaseViewModel

/**
 * ViewModel для экрана счета пользователя.
 *
 * Управляет загрузкой данных счета, обработкой ошибок и состоянием UI.
 * Реализует MVI паттерн для управления состоянием.
 *
 * @param getAccountUseCase Use case для получения данных счета
 * @param accountUIMapper Маппер для преобразования доменной модели в UI модель
 * @param resourceProvider Провайдер для доступа к строковым ресурсам
 */

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val accountUIMapper: AccountUIMapper,
    private val resourceProvider: ResourceProvider
) : BaseViewModel<AccountState, AccountEvent, AccountEffect>() {

    override val _state = MutableStateFlow(AccountState())
    override val state: StateFlow<AccountState> = _state.asStateFlow()

    override val _effect = MutableSharedFlow<AccountEffect>()
    override val effect: SharedFlow<AccountEffect> = _effect.asSharedFlow()

    private var loadAccountJob: Job? = null

    override fun reduce(event: AccountEvent) {
        when (event) {
            AccountEvent.HideErrorDialog ->
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
            AccountEvent.LoadAccount -> {
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
                loadAccount()
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
                            isLoading = false,
                            account = accountUIMapper.map(result.data)
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
            else -> resourceProvider.getString(R.string.failure_unknown)
        }
        _state.update {
            it.copy(
                isLoading = false,
                account = AccountUIModel(),
                showErrorDialog = message
            )
        }
    }
}