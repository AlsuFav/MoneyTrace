package ru.fav.moneytrace.account.api.repository

import ru.fav.moneytrace.account.api.model.AccountDetailsModel
import ru.fav.moneytrace.account.api.model.AccountModel
import ru.fav.moneytrace.util.result.Result

/**
 * Репозиторий для управления данными счетов пользователя.
 *
 * Предоставляет методы для получения информации о счетах и их детальных данных.
 */

interface AccountRepository {
    suspend fun getAllAccounts() : Result<List<AccountModel>>
    suspend fun getAccountDetails(id: Int) : Result<AccountDetailsModel>
}