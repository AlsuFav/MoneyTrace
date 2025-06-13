package ru.fav.moneytrace.data.repository

import ru.fav.moneytrace.domain.model.Transaction
import ru.fav.moneytrace.domain.repository.TransactionRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
): TransactionRepository {

    override suspend fun getTransactionsByPeriod(
        startDate: String,
        endDate: String
    ): List<Transaction> {
        return listOf(
            // Доходы
            Transaction(
                id = 1,
                category = "Зарплата",
                emoji = "💰",
                isIncome = true,
                currency = "RUB",
                amount = "85000"
            ),
            Transaction(
                id = 2,
                category = "Фриланс",
                emoji = "💻",
                isIncome = true,
                comment = "Проект для клиента",
                currency = "RUB",
                amount = "25000"
            ),
            Transaction(
                id = 3,
                category = "Подарок",
                emoji = "🎁",
                isIncome = true,
                currency = "RUB",
                amount = "5000"
            ),

            // Расходы
            Transaction(
                id = 4,
                category = "Продукты",
                emoji = "🛒",
                isIncome = false,
                currency = "RUB",
                amount = "3500"
            ),
            Transaction(
                id = 5,
                category = "Транспорт",
                emoji = "🚇",
                isIncome = false,
                currency = "RUB",
                amount = "2500"
            ),
            Transaction(
                id = 6,
                category = "Рестораны",
                emoji = "🍽️",
                isIncome = false,
                comment = "Ужин с друзьями",
                currency = "RUB",
                amount = "4200"
            ),
            Transaction(
                id = 7,
                category = "Развлечения",
                emoji = "🎬",
                isIncome = false,
                comment = "Кино и попкорн",
                currency = "RUB",
                amount = "1200"
            ),
            Transaction(
                id = 8,
                category = "Здоровье",
                emoji = "💊",
                isIncome = false,
                currency = "RUB",
                amount = "2300"
            ),
            Transaction(
                id = 9,
                category = "Одежда",
                emoji = "👕",
                isIncome = false,
                currency = "RUB",
                amount = "8500"
            ),
            Transaction(
                id = 10,
                category = "Коммунальные услуги",
                emoji = "🏠",
                isIncome = false,
                comment = "Квартплата за июнь",
                currency = "RUB",
                amount = "12000"
            ),
            Transaction(
                id = 11,
                category = "Связь",
                emoji = "📱",
                isIncome = false,
                currency = "RUB",
                amount = "1500"
            ),
            Transaction(
                id = 12,
                category = "Образование",
                emoji = "📚",
                isIncome = false,
                comment = "Онлайн курс по Android",
                currency = "RUB",
                amount = "15000"
            ),
            Transaction(
                id = 13,
                category = "Спорт",
                emoji = "🏋️",
                isIncome = false,
                currency = "RUB",
                amount = "3000"
            ),
            Transaction(
                id = 14,
                category = "Подарки",
                emoji = "🎁",
                isIncome = false,
                comment = "Подарок маме",
                currency = "RUB",
                amount = "5000"
            ),
            Transaction(
                id = 15,
                category = "Инвестиции",
                emoji = "📈",
                isIncome = true,
                currency = "RUB",
                amount = "3200"
            )
        )
    }
}
