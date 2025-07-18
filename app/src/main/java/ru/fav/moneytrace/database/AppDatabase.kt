package ru.fav.moneytrace.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.fav.moneytrace.account.impl.data.local.dao.AccountDao
import ru.fav.moneytrace.account.impl.data.local.entity.AccountCategoryDetailsEntity
import ru.fav.moneytrace.account.impl.data.local.entity.AccountDetailsEntity
import ru.fav.moneytrace.account.impl.data.local.entity.AccountEntity
import ru.fav.moneytrace.categories.impl.data.local.dao.CategoryDao
import ru.fav.moneytrace.categories.impl.data.local.entity.CategoryEntity
import ru.fav.moneytrace.transaction.impl.data.local.dao.TransactionDao
import ru.fav.moneytrace.transaction.impl.data.local.entity.TransactionEntity

@Database(
    entities = [AccountEntity::class,
        AccountCategoryDetailsEntity::class,
        AccountDetailsEntity::class,
        CategoryEntity::class,
        TransactionEntity::class
               ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun accountDao(): AccountDao
}