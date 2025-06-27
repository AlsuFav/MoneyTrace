package ru.fav.moneytrace.domain.provider

/**
 * Провайдер для доступа к строковым ресурсам приложения.
 *
 * Абстрагирует получение строк из ресурсов Android для использования в доменном слое.
 */

interface ResourceProvider {
    fun getString(stringResId: Int): String
    fun getString(stringResId: Int, vararg args: Any): String
}
