package ru.fav.moneytrace.account.impl.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.moneytrace.account.api.repository.AccountRepository
import ru.fav.moneytrace.domain.di.qualifier.IoDispatchers
import javax.inject.Inject
import ru.fav.moneytrace.util.result.Result

class GetAllCurrenciesUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Result<List<String>> {
        return withContext(dispatcher) {
            when (val result = accountRepository.getAllCurrencies()) {
                is Result.Success -> {
                    result
                }

                is Result.Failure -> result
            }
        }
    }
}