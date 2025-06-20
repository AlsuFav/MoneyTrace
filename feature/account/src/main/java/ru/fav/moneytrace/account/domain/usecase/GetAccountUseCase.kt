package ru.fav.moneytrace.account.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.moneytrace.domain.di.qualifier.IoDispatchers
import ru.fav.moneytrace.domain.model.AccountModel
import ru.fav.moneytrace.domain.repository.AccountRepository
import ru.fav.moneytrace.util.result.Result
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Result<AccountModel> {
        return withContext(dispatcher) {
            when (val result = accountRepository.getAllAccounts()) {
                is Result.Success -> {
                    val account = result.data[0]
                    Result.Success(account)
                }

                is Result.NetworkError -> result
                is Result.HttpError -> result
            }
        }
    }
}