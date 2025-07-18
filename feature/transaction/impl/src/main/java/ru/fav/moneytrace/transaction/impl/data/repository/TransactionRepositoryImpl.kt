package ru.fav.moneytrace.transaction.impl.data.repository

import ru.fav.moneytrace.network.util.ApiClient
import ru.fav.moneytrace.transaction.api.model.TransactionModel
import ru.fav.moneytrace.transaction.api.repository.TransactionRepository
import ru.fav.moneytrace.transaction.impl.data.local.dao.TransactionDao
import ru.fav.moneytrace.transaction.impl.data.mapper.TransactionMapper
import ru.fav.moneytrace.transaction.impl.data.remote.TransactionApi
import ru.fav.moneytrace.transaction.impl.data.remote.pojo.request.TransactionRequest
import ru.fav.moneytrace.util.DateHelper
import ru.fav.moneytrace.util.result.FailureReason
import javax.inject.Inject
import ru.fav.moneytrace.util.result.Result

class TransactionRepositoryImpl @Inject constructor(
    private val transactionApi: TransactionApi,
    private val transactionDao: TransactionDao,
    private val transactionMapper: TransactionMapper,
    private val apiClient: ApiClient,
): TransactionRepository {

    override suspend fun getTransactionsByAccountAndPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?,
    ): Result<List<TransactionModel>> {
        return when (val networkResult = apiClient.call {
            transactionApi.getTransactionsByAccountAndPeriod(
                accountId = accountId,
                startDate = startDate,
                endDate = endDate
            )
        }) {
            is Result.Success -> {
                val domainModels = transactionMapper.mapNetworkToDomainList(networkResult.data)
                val entities = transactionMapper.mapDomainToEntityList(domainModels)

                transactionDao.deleteTransactionsByAccount(accountId)
                transactionDao.insertTransactions(entities)

                Result.Success(domainModels)
            }
            is Result.Failure -> {
                if (networkResult.reason is FailureReason.Network) {
                    val startTimestamp = startDate?.let { DateHelper.parseApiDateTime(it).time }
                    val endTimestamp = endDate?.let { DateHelper.parseApiDateTime(it).time }

                    val cachedTransactions = transactionDao.getTransactionsByAccountAndPeriod(
                        accountId, startTimestamp, endTimestamp
                    )

                    if (cachedTransactions.isNotEmpty()) {
                        val domainModels = transactionMapper.mapEntityToDomainList(cachedTransactions)
                        Result.Success(domainModels)
                    } else {
                        networkResult
                    }
                } else {
                    networkResult
                }
            }
        }
    }

    override suspend fun getTransactionById(id: Int): Result<TransactionModel> {
        return when (val networkResult = apiClient.call { transactionApi.getTransactionById(id) }) {
            is Result.Success -> {
                val domainModel = transactionMapper.mapNetworkToDomain(networkResult.data)
                val entity = transactionMapper.mapDomainToEntity(domainModel)
                transactionDao.insertTransaction(entity)
                Result.Success(domainModel)
            }
            is Result.Failure -> {
                if (networkResult.reason is FailureReason.Network) {
                    val cachedTransaction = transactionDao.getTransactionById(id)
                    if (cachedTransaction != null) {
                        val domainModel = transactionMapper.mapEntityToDomain(cachedTransaction)
                        Result.Success(domainModel)
                    } else {
                        networkResult
                    }
                } else {
                    networkResult
                }
            }
        }
    }

    override suspend fun createTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?,
    ): Result<TransactionModel> {
        return when (val result = apiClient.call {
            transactionApi.createTransaction(
                TransactionRequest(
                    accountId = accountId,
                    categoryId = categoryId,
                    amount = amount,
                    transactionDate = transactionDate,
                    comment = comment,
                )
            )
        }) {
            is Result.Success -> {
                val domainModel = transactionMapper.mapNetworkToDomain(result.data)
                val entity = transactionMapper.mapDomainToEntity(domainModel)
                transactionDao.insertTransaction(entity)
                Result.Success(domainModel)
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
        return when (val result = apiClient.call {
            transactionApi.updateTransaction(
                id = id,
                TransactionRequest(
                    accountId = accountId,
                    categoryId = categoryId,
                    amount = amount,
                    transactionDate = transactionDate,
                    comment = comment,
                )
            )
        }) {
            is Result.Success -> {
                val domainModel = transactionMapper.mapNetworkToDomain(result.data)
                val entity = transactionMapper.mapDomainToEntity(domainModel)
                transactionDao.insertTransaction(entity)
                Result.Success(domainModel)
            }
            is Result.Failure -> result
        }
    }

    override suspend fun deleteTransactionById(id: Int): Result<Unit> {
        return when (val result = apiClient.call { transactionApi.deleteTransactionById(id = id) }) {
            is Result.Success -> {
                transactionDao.deleteTransactionById(id)
                Result.Success(Unit)
            }
            is Result.Failure -> result
        }
    }
}