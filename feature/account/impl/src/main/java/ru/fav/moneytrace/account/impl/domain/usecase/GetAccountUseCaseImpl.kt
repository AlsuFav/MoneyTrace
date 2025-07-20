package ru.fav.moneytrace.account.impl.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.moneytrace.account.api.model.AccountModel
import ru.fav.moneytrace.account.api.repository.AccountRepository
import ru.fav.moneytrace.account.api.usecase.GetAccountUseCase
import ru.fav.moneytrace.domain.di.qualifier.IoDispatchers
import ru.fav.moneytrace.util.result.Result
import javax.inject.Inject

/**
 * Use case для получения основного счета пользователя.
 *
 * Возвращает первый счет из списка всех счетов пользователя.
 *
 * @param accountRepository Репозиторий для доступа к данным счетов
 * @param dispatcher Диспетчер для выполнения операций в фоновом потоке
 */

class GetAccountUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) : GetAccountUseCase {
    override suspend operator fun invoke(): Result<AccountModel> {
        return withContext(dispatcher) {
            when (val result = accountRepository.getAllAccounts()) {
                is Result.Success -> {
                    val account = result.data[0]
                    Result.Success(account, cached = result.cached)
                }

                is Result.Failure -> result
            }
        }
    }
}