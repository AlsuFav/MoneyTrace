package ru.fav.moneytrace.domain.repository

import ru.fav.moneytrace.domain.model.AccountDetailsModel
import ru.fav.moneytrace.domain.model.AccountModel
import ru.fav.moneytrace.util.result.Result

interface AccountRepository {
    suspend fun getAllAccounts() : Result<List<AccountModel>>
    suspend fun getAccountDetails(id: Int) : Result<AccountDetailsModel>
}