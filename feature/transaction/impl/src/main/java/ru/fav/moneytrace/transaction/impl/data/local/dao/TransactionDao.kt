package ru.fav.moneytrace.transaction.impl.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.fav.moneytrace.transaction.impl.data.local.entity.AccountStateEntity
import ru.fav.moneytrace.transaction.impl.data.local.entity.TransactionCategoryEntity
import ru.fav.moneytrace.transaction.impl.data.local.entity.TransactionEntity
import ru.fav.moneytrace.transaction.impl.data.local.util.TransactionWithJoinData

@Dao
interface TransactionDao {

    @Query("""
        SELECT t.*, 
               a.name as account_name, a.balance as account_balance, a.currency as account_currency,
               c.name as category_name, c.emoji as category_emoji, c.is_income as category_is_income
        FROM transactions t
        LEFT JOIN account_states a ON t.account_id = a.id
        LEFT JOIN transaction_categories c ON t.category_id = c.id
        WHERE t.account_id = :accountId 
        AND (:startDate IS NULL OR t.transaction_date >= :startDate)
        AND (:endDate IS NULL OR t.transaction_date <= :endDate)
        ORDER BY t.transaction_date DESC
    """)
    suspend fun getTransactionsByAccountAndPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<TransactionWithJoinData>

    @Query("""
        SELECT t.*, 
               a.name as account_name, a.balance as account_balance, a.currency as account_currency,
               c.name as category_name, c.emoji as category_emoji, c.is_income as category_is_income
        FROM transactions t
        LEFT JOIN account_states a ON t.account_id = a.id
        LEFT JOIN transaction_categories c ON t.category_id = c.id
        WHERE t.id = :id
    """)
    suspend fun getTransactionById(id: Int): TransactionWithJoinData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(transactions: List<TransactionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccountState(account: AccountStateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccountStates(accounts: List<AccountStateEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: TransactionCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<TransactionCategoryEntity>)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: Int)

    @Query("DELETE FROM transactions WHERE account_id = :accountId")
    suspend fun deleteTransactionsByAccount(accountId: Int)

    @Query("DELETE FROM transactions")
    suspend fun clearAllTransactions()

    @androidx.room.Transaction
    suspend fun insertTransactionWithRelations(
        transaction: TransactionEntity,
        account: AccountStateEntity,
        category: TransactionCategoryEntity
    ) {
        insertAccountState(account)
        insertCategory(category)
        insertTransaction(transaction)
    }

    @androidx.room.Transaction
    suspend fun insertTransactionsWithRelations(
        transactions: List<TransactionEntity>,
        accounts: List<AccountStateEntity>,
        categories: List<TransactionCategoryEntity>
    ) {
        insertAccountStates(accounts)
        insertCategories(categories)
        insertTransactions(transactions)
    }
}