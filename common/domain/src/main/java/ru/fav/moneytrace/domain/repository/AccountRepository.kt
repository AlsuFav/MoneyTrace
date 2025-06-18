package ru.fav.moneytrace.domain.repository

import ru.fav.moneytrace.domain.model.Account
import ru.fav.moneytrace.domain.model.Stat

interface AccountRepository {
    suspend fun getAccount() : Account
    suspend fun getAllExpenseStats() : List<Stat>
}