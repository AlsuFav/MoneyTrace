package ru.fav.moneytrace.transaction.impl.data.repository

import ru.fav.moneytrace.network.util.ApiClient
import ru.fav.moneytrace.transaction.api.model.TransactionModel
import ru.fav.moneytrace.transaction.api.repository.TransactionRepository
import ru.fav.moneytrace.transaction.impl.data.mapper.TransactionMapper
import ru.fav.moneytrace.transaction.impl.data.remote.TransactionApi
import javax.inject.Inject
import ru.fav.moneytrace.util.result.Result

class TransactionRepositoryImpl @Inject constructor(
    private val transactionApi: TransactionApi,
    private val transactionMapper: TransactionMapper,
    private val apiClient: ApiClient,
): TransactionRepository {

    override suspend fun getTransactionsByAccountAndPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?,
    ): Result<List<TransactionModel>> {
        return when (val result = apiClient.call { transactionApi.getTransactionsByAccountAndPeriod(
            accountId = accountId,
            startDate = startDate,
            endDate = endDate
        ) }) {
            is Result.Success -> {
                val transactions = transactionMapper.mapList(result.data)
                Result.Success(transactions)
            }
            is Result.Failure -> result
        }
    }

}
