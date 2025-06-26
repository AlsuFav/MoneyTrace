package ru.fav.moneytrace.account.api.repository

import ru.fav.moneytrace.account.api.model.AccountDetailsModel
import ru.fav.moneytrace.account.api.model.AccountModel
import ru.fav.moneytrace.util.result.Result

interface AccountRepository {
    suspend fun getAllAccounts() : Result<List<AccountModel>>
    suspend fun getAccountDetails(id: Int) : Result<AccountDetailsModel>
}