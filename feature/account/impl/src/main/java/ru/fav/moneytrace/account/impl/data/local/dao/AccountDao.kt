package ru.fav.moneytrace.account.impl.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.fav.moneytrace.account.impl.data.local.entity.AccountCategoryDetailsEntity
import ru.fav.moneytrace.account.impl.data.local.entity.AccountDetailsEntity
import ru.fav.moneytrace.account.impl.data.local.entity.AccountEntity

@Dao
interface AccountDao {

    @Query("SELECT * FROM accounts")
    suspend fun getAllAccounts(): List<AccountEntity>

    @Query("SELECT * FROM account_details WHERE id = :id")
    suspend fun getAccountDetailsById(id: Int): AccountDetailsEntity?

    @Query("""
        SELECT * FROM account_category_details 
        WHERE account_id = :accountId AND is_income = :isIncome
    """)
    suspend fun getAccountCategoriesByType(accountId: Int, isIncome: Boolean): List<AccountCategoryDetailsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccounts(accounts: List<AccountEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccountDetails(accountDetails: AccountDetailsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccountCategories(categories: List<AccountCategoryDetailsEntity>)

    @Query("DELETE FROM accounts")
    suspend fun clearAllAccounts()

    @Query("DELETE FROM account_details WHERE id = :accountId")
    suspend fun clearAccountDetails(accountId: Int)

    @Query("DELETE FROM account_category_details WHERE account_id = :accountId")
    suspend fun clearAccountCategories(accountId: Int)

    @Transaction
    suspend fun insertAccountDetailsWithCategories(
        accountDetails: AccountDetailsEntity,
        categories: List<AccountCategoryDetailsEntity>
    ) {
        clearAccountDetails(accountDetails.id)
        clearAccountCategories(accountDetails.id)
        insertAccountDetails(accountDetails)
        insertAccountCategories(categories)
    }
}