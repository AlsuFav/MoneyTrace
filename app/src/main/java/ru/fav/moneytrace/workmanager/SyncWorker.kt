package ru.fav.moneytrace.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.fav.moneytrace.account.api.repository.AccountRepository
import ru.fav.moneytrace.categories.api.repository.CategoryRepository
import ru.fav.moneytrace.transaction.api.repository.TransactionRepository

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val accountRepository: AccountRepository,
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val WORK_NAME = "sync_data_work"
    }

    override suspend fun doWork(): Result {
        return Result.success()
    }
}