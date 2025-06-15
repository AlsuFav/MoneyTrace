package ru.fav.moneytrace.account.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.fav.moneytrace.domain.usecase.GetAccountUseCase
import ru.fav.moneytrace.domain.usecase.GetExpenseStatsUseCase
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase
): ViewModel() {

    private val _state = MutableStateFlow(AccountState())
    val state: StateFlow<AccountState> = _state.asStateFlow()

    fun reduce(event: AccountEvent) {
        when (event) {
            else -> {}
        }
    }

    init {
        loadAccount()
    }

    private fun loadAccount() {
        viewModelScope.launch {
            runCatching {
                getAccountUseCase()
            }.fold(
                onSuccess = {
                        account ->
                    _state.update {
                        it.copy(
                            account = account,
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