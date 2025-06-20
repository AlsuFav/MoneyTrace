package ru.fav.moneytrace.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.fav.moneytrace.network.pojo.response.TransactionResponse

interface TransactionApi {

    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionsByAccountAndPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null
    ): List<TransactionResponse>
}