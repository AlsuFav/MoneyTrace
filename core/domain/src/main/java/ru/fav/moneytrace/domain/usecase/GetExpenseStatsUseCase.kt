package ru.fav.moneytrace.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.moneytrace.domain.di.qualifier.IoDispatchers
import ru.fav.moneytrace.domain.model.Stat
import ru.fav.moneytrace.domain.repository.AccountRepository
import javax.inject.Inject

class GetExpenseStatsUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    @IoDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(query: String = ""): List<Stat> {
        return withContext(dispatcher) {
            val allStats = accountRepository.getAllExpenseStats()
            if (query.isBlank()) {
                allStats
            } else {
                allStats.filter { stat ->
                    stat.categoryName.contains(query, ignoreCase = true)
                }
            }
        }
    }
}