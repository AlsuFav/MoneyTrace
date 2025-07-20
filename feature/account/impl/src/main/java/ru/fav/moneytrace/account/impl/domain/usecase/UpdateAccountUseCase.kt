package ru.fav.moneytrace.account.impl.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.moneytrace.account.api.model.AccountModel
import ru.fav.moneytrace.account.api.repository.AccountRepository
import ru.fav.moneytrace.domain.di.qualifier.IoDispatchers
import ru.fav.moneytrace.util.result.Result
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        id: Int,
        name: String,
        balance: String,
        currency: String,
    ): Result<AccountModel> {
        return withContext(dispatcher) {
            when (val result = accountRepository.updateAccount(
                id = id,
                name = name,
                balance = balance,
                currency = currency
            )) {
                is Result.Success -> {
                    val account = result.data
                    Result.Success(account, cached = result.cached)
                }

                is Result.Failure -> result
            }
        }
    }
}