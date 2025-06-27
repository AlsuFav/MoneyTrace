package ru.fav.moneytrace.transaction.impl.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.fav.moneytrace.transaction.impl.data.remote.pojo.response.TransactionResponse

/**
 * Интерфейс API для взаимодействия с серверными эндпоинтами транзакций.
 *
 * Определяет методы для получения данных о транзакциях с сервера
 * с использованием Retrofit аннотаций для описания HTTP-запросов.
 */

interface TransactionApi {

    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionsByAccountAndPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null
    ): List<TransactionResponse>
}