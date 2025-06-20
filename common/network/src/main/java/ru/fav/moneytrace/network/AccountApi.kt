package ru.fav.moneytrace.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.fav.moneytrace.network.pojo.response.AccountDetailsResponse
import ru.fav.moneytrace.network.pojo.response.AccountResponse

interface AccountApi {

    @GET("accounts")
    suspend fun getAllAccounts(): List<AccountResponse>?

    @GET("accounts/{id}")
    suspend fun getAccountById(@Path("id") accountId: Int): AccountDetailsResponse?
}
