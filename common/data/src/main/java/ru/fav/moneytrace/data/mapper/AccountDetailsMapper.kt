package ru.fav.moneytrace.data.mapper

import ru.fav.moneytrace.domain.model.AccountDetailsModel
import ru.fav.moneytrace.network.pojo.response.AccountDetailsResponse
import ru.fav.moneytrace.util.DateHelper
import java.util.Date
import javax.inject.Inject

class AccountDetailsMapper @Inject constructor(
    private val categoryDetailsMapper: CategoryDetailsMapper
) {

    fun map(input: AccountDetailsResponse?): AccountDetailsModel {
        return input?.let {
            AccountDetailsModel (
            id = input.id ?: 0,
            name = input.name.orEmpty(),
            balance = input.balance?.toDoubleOrNull() ?: 0.0,
            currency = input.currency.orEmpty(),
            incomeCategories = categoryDetailsMapper.mapList(input.incomeStats),
            expenseCategories = categoryDetailsMapper.mapList(input.expenseStats),
            createdAt = input.createdAt?.let { DateHelper.parseApiDateTime(it) } ?: Date(),
            updatedAt = input.updatedAt?.let { DateHelper.parseApiDateTime(it) } ?: Date(),
            )
        } ?: AccountDetailsModel()
    }
}