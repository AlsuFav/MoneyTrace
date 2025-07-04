package ru.fav.moneytrace.account.impl.ui.mapper

import ru.fav.moneytrace.account.impl.ui.model.CurrencyUIModel
import ru.fav.moneytrace.domain.provider.ResourceProvider
import ru.fav.moneytrace.ui.util.extension.toCurrencyNameResId
import ru.fav.moneytrace.ui.util.extension.toCurrencySymbol
import javax.inject.Inject

class CurrencyUIMapper @Inject constructor(
    private val resourceProvider: ResourceProvider
) {
    fun map(currency: String): CurrencyUIModel {
        return CurrencyUIModel(
            currency = currency,
            name = resourceProvider.getString(currency.toCurrencyNameResId()),
            symbol = currency.toCurrencySymbol()
        )
    }

    fun mapList(currencies: List<String>): List<CurrencyUIModel> {
        return currencies.map { map(it) }
    }
}