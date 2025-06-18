package ru.fav.moneytrace.data.repository

import ru.fav.moneytrace.domain.model.Account
import ru.fav.moneytrace.domain.model.Stat
import ru.fav.moneytrace.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
): AccountRepository {
    override suspend fun getAccount(): Account {
        return Account(
            id = 1,
            name = "Основной счёт",
            balance = "1000.00",
            currency = "RUB",
        )
    }

    override suspend fun getAllExpenseStats(): List<Stat> {
        return listOf(
            Stat(
                categoryId = 7,
                categoryName = "Жильё",
                emoji = "🏠",
                amount = "35000.00",
            ),
            Stat(
                categoryId = 8,
                categoryName = "Продукты",
                emoji = "🍎",
                amount = "12500.00",
            ),
            Stat(
                categoryId = 9,
                categoryName = "Транспорт",
                emoji = "🚗",
                amount = "8500.00",
            ),
            Stat(
                categoryId = 10,
                categoryName = "Развлечения",
                emoji = "🎭",
                amount = "6000.00",
            ),
            Stat(
                categoryId = 11,
                categoryName = "Рестораны",
                emoji = "🍽️",
                amount = "7500.00",
            ),
            Stat(
                categoryId = 12,
                categoryName = "Одежда",
                emoji = "👕",
                amount = "9000.00",
            ),
            Stat(
                categoryId = 13,
                categoryName = "Здоровье",
                emoji = "🏥",
                amount = "12000.00",
            ),
            Stat(
                categoryId = 15,
                categoryName = "Техника",
                emoji = "📱",
                amount = "30000.00",
            ),
        )
    }
}
