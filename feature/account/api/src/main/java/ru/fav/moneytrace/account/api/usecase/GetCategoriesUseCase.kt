package ru.fav.moneytrace.account.api.usecase

import kotlinx.coroutines.CoroutineDispatcher
import ru.fav.moneytrace.account.api.model.AccountModel
import ru.fav.moneytrace.util.result.Result

interface GetAccountUseCase {
    suspend operator fun invoke(): Result<AccountModel>
}