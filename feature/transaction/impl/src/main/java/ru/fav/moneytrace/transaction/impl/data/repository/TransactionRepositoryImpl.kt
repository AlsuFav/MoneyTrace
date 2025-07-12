package ru.fav.moneytrace.transaction.impl.data.repository

import ru.fav.moneytrace.network.util.ApiClient
import ru.fav.moneytrace.transaction.api.model.TransactionModel
import ru.fav.moneytrace.transaction.api.repository.TransactionRepository
import ru.fav.moneytrace.transaction.impl.data.mapper.TransactionMapper
import ru.fav.moneytrace.transaction.impl.data.remote.TransactionApi
import ru.fav.moneytrace.transaction.impl.data.remote.pojo.request.TransactionRequest
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

    override suspend fun getTransactionById(id: Int): Result<TransactionModel> {
        return when (val result = apiClient.call { transactionApi.getTransactionById(id)}) {
            is Result.Success -> {
                val transactions = transactionMapper.map(result.data)
                Result.Success(transactions)
            }
            is Result.Failure -> result
        }
    }

    override suspend fun createTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?,
    ): Result<TransactionModel> {
        return when (val result = apiClient.call { transactionApi.createTransaction(
            TransactionRequest(
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
                transactionDate = transactionDate,
                comment = comment,
            )
        ) }) {
            is Result.Success -> {
                val transactions = transactionMapper.map(result.data)
                Result.Success(transactions)
            }
            is Result.Failure -> result
        }
    }

    override suspend fun updateTransaction(
        id: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?,
    ): Result<TransactionModel> {
        return when (val result = apiClient.call { transactionApi.updateTransaction(
            id = id,
            TransactionRequest(
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
                transactionDate = transactionDate,
                comment = comment,
            )
        ) }) {
            is Result.Success -> {
                val transactions = transactionMapper.map(result.data)
                Result.Success(transactions)
            }
            is Result.Failure -> result
        }
    }

    override suspend fun deleteTransactionBuId(id: Int): Result<Unit> {
        return when (val result = apiClient.call { transactionApi.deleteTransactionById(id = id,) }) {
            is Result.Success -> {
                Result.Success(Unit)
            }
            is Result.Failure -> result
        }
    }
}
