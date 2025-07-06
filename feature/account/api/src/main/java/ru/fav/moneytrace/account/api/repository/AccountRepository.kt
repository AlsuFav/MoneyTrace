package ru.fav.moneytrace.account.api.repository

import ru.fav.moneytrace.account.api.model.AccountDetailsModel
import ru.fav.moneytrace.account.api.model.AccountModel
import ru.fav.moneytrace.util.result.Result

/**
 * Репозиторий для управления данными счетов пользователя.
 *
 * Предоставляет методы для получения информации о счетах, их детальных данных,
 * обновления и доступных для счетов валют.
 */

interface AccountRepository {
    suspend fun getAllAccounts() : Result<List<AccountModel>>
    suspend fun getAccountDetails(id: Int) : Result<AccountDetailsModel>
    suspend fun updateAccount(
        id: Int,
        name: String,
        balance: String,
        currency: String,
    ) : Result<AccountModel>
    suspend fun getAllCurrencies() : Result<List<String>>
}