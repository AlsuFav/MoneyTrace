package ru.fav.moneytrace.data.repository

import ru.fav.moneytrace.data.mapper.TransactionMapper
import ru.fav.moneytrace.domain.model.TransactionModel
import ru.fav.moneytrace.domain.repository.TransactionRepository
import ru.fav.moneytrace.network.TransactionApi
import ru.fav.moneytrace.network.util.ApiClient
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
            is Result.NetworkError -> result
            is Result.HttpError -> result
        }
    }

}
