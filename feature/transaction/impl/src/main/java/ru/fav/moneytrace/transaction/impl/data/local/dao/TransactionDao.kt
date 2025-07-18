package ru.fav.moneytrace.transaction.impl.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.fav.moneytrace.transaction.impl.data.local.entity.TransactionEntity

@Dao
interface TransactionDao {

    @Query("""
        SELECT * FROM transactions 
        WHERE account_id = :accountId 
        AND (:startDate IS NULL OR transaction_date >= :startDate)
        AND (:endDate IS NULL OR transaction_date <= :endDate)
        ORDER BY transaction_date DESC
    """)
    suspend fun getTransactionsByAccountAndPeriod(
        accountId: Int,
        startDate: Long?,
        endDate: Long?
    ): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: Int): TransactionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(transactions: List<TransactionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: Int)

    @Query("DELETE FROM transactions WHERE account_id = :accountId")
    suspend fun deleteTransactionsByAccount(accountId: Int)

    @Query("DELETE FROM transactions")
    suspend fun clearAll()
}