package ru.fav.moneytrace.account.impl.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import ru.fav.moneytrace.account.impl.data.remote.pojo.response.AccountDetailsResponse
import ru.fav.moneytrace.account.impl.data.remote.pojo.response.AccountResponse

interface AccountApi {

    @GET("accounts")
    suspend fun getAllAccounts(): List<AccountResponse>?

    @GET("accounts/{id}")
    suspend fun getAccountById(@Path("id") accountId: Int): AccountDetailsResponse?
}
