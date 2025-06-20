package ru.fav.moneytrace.data.repository

import ru.fav.moneytrace.data.mapper.AccountDetailsMapper
import ru.fav.moneytrace.data.mapper.AccountMapper
import ru.fav.moneytrace.domain.model.AccountDetailsModel
import ru.fav.moneytrace.domain.model.AccountModel
import ru.fav.moneytrace.domain.repository.AccountRepository
import ru.fav.moneytrace.network.AccountApi
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
            is Result.NetworkError -> result
            is Result.HttpError -> result
        }
    }

    override suspend fun getAccountDetails(id: Int): Result<AccountDetailsModel> {
        return when (val result = apiClient.call { accountApi.getAccountById(id) }) {
            is Result.Success -> {
                val accountDetails = accountDetailsMapper.map(result.data)
                Result.Success(accountDetails)
            }
            is Result.NetworkError -> result
            is Result.HttpError -> result
        }
    }
}
