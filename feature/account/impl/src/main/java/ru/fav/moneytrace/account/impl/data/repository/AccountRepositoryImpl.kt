package ru.fav.moneytrace.account.impl.data.repository

import ru.fav.moneytrace.account.api.model.AccountDetailsModel
import ru.fav.moneytrace.account.api.model.AccountModel
import ru.fav.moneytrace.account.api.repository.AccountRepository
import ru.fav.moneytrace.account.impl.data.mapper.AccountDetailsMapper
import ru.fav.moneytrace.account.impl.data.mapper.AccountMapper
import ru.fav.moneytrace.account.impl.data.remote.AccountApi
import ru.fav.moneytrace.network.util.ApiClient
import ru.fav.moneytrace.util.result.Result
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountApi: AccountApi,
    private val accountMapper: AccountMapper,
    private val accountDetailsMapper: AccountDetailsMapper,
    private val apiClient: ApiClient,
): AccountRepository {

    override suspend fun getAllAccounts(): Result<List<AccountModel>> {
        return when (val result = apiClient.call { accountApi.getAllAccounts() }) {
            is Result.Success -> {
                val accounts = accountMapper.mapList(result.data)
                Result.Success(accounts)
            }
            is Result.Failure -> result
        }
    }

    override suspend fun getAccountDetails(id: Int): Result<AccountDetailsModel> {
        return when (val result = apiClient.call { accountApi.getAccountById(id) }) {
            is Result.Success -> {
                val accountDetails = accountDetailsMapper.map(result.data)
                Result.Success(accountDetails)
            }
            is Result.Failure -> result
        }
    }
}
