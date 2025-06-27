package ru.fav.moneytrace.data.provider

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.fav.moneytrace.domain.provider.ResourceProvider
import javax.inject.Inject

/**
 * Реализация провайдера ресурсов для доступа к строковым ресурсам Android.
 *
 * @param context Контекст приложения для доступа к ресурсам
 */

class ResourceProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ResourceProvider {

    override fun getString(stringResId: Int): String {
        return context.getString(stringResId)
    }

    override fun getString(stringResId: Int, vararg args: Any): String {
        return context.getString(stringResId, *args)
    }
}
