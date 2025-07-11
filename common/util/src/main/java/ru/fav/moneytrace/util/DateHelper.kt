package ru.fav.moneytrace.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Утилитный класс для работы с датами и их форматированием.
 *
 * Предоставляет методы для преобразования дат между различными форматами:
 * - API формат для отправки на сервер
 * - Отображаемый формат для пользователя
 * - Парсинг дат из строк
 *
 * Поддерживает работу с UTC временной зоной для API запросов.
 */

object DateHelper {
    private const val API_DATE_FORMAT = "yyyy-MM-dd"
    private const val API_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val API_DATETIME_FORMAT_NO_MILLIS = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private const val API_DATETIME_FORMAT_EXTENDED = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"
    private const val DISPLAY_DATE_FORMAT = "dd.MM.yyyy"
    private const val DISPLAY_DATETIME_FORMAT = "dd.MM.yyyy HH:mm"
    private const val DISPLAY_TIME_FORMAT = "HH:mm"

    private val apiDateFormatter = SimpleDateFormat(API_DATE_FORMAT, Locale.getDefault())
    private val apiDateTimeFormatter = SimpleDateFormat(API_DATETIME_FORMAT, Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    private val apiDateTimeFormatterNoMillis = SimpleDateFormat(API_DATETIME_FORMAT_NO_MILLIS, Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    private val apiDateTimeFormatterExtended = SimpleDateFormat(API_DATETIME_FORMAT_EXTENDED, Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    private val displayDateFormatter = SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.getDefault())
    private val displayDateTimeFormatter = SimpleDateFormat(DISPLAY_DATETIME_FORMAT, Locale.getDefault())
    private val displayTimeFormatter = SimpleDateFormat(DISPLAY_TIME_FORMAT, Locale.getDefault())

    fun getTodayApiFormat(): String {
        return apiDateFormatter.format(Date())
    }

    fun getCurrentMonthStartDisplayFormat(): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        return displayDateFormatter.format(calendar.time)
    }

    fun getTodayDisplayFormat(): String {
        return displayDateFormatter.format(Date())
    }
    fun getCurrentTimeDisplayFormat(): String {
        return displayTimeFormatter.format(Date())
    }

    fun dateToApiFormat(date: Date): String {
        return apiDateFormatter.format(date)
    }

    fun dateTimeToApiFormat(date: Date): String {
        return apiDateTimeFormatter.format(date)
    }

    fun parseApiDate(apiDateString: String): Date {
        return try {
            apiDateFormatter.parse(apiDateString) ?: Date()
        } catch (e: Exception) {
            Date()
        }
    }

    fun parseDisplayDate(displayDateString: String): Date {
        return try {
            displayDateFormatter.parse(displayDateString) ?: Date()
        } catch (e: Exception) {
            Date()
        }
    }

    fun parseApiDateTime(apiDateTimeString: String): Date {
        val formatters = listOf(
            apiDateTimeFormatterExtended,
            apiDateTimeFormatter,
            apiDateTimeFormatterNoMillis
        )

        for (formatter in formatters) {
            try {
                formatter.parse(apiDateTimeString)?.let { return it }
            } catch (e: Exception) {
            }
        }

        return Date()
    }

    fun formatDateForDisplay(date: Date): String? {
        return try {
            displayDateFormatter.format(date)
        } catch (e: Exception) {
            null
        }
    }

    fun formatDateTimeForDisplay(date: Date): String? {
        return try {
            displayDateTimeFormatter.format(date)
        } catch (e: Exception) {
            null
        }
    }

    fun formatTimeForDisplay(date: Date): String? {
        return try {
            displayTimeFormatter.format(date)
        } catch (e: Exception) {
            null
        }
    }

    fun parseDisplayDateAndTime(dateString: String, timeString: String): Date {
        return try {
            val combinedString = "$dateString $timeString"
            displayDateTimeFormatter.parse(combinedString) ?: Date()
        } catch (e: Exception) {
            Date()
        }
    }
}
