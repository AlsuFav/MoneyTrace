package ru.fav.moneytrace.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateHelper {
    private const val API_DATE_FORMAT = "yyyy-MM-dd"
    private const val API_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val DISPLAY_DATE_FORMAT = "dd.MM.yyyy"
    private const val DISPLAY_DATETIME_FORMAT = "dd.MM.yyyy HH:mm"
    private const val DISPLAY_TIME_FORMAT = "HH:mm"

    private val apiDateFormatter = SimpleDateFormat(API_DATE_FORMAT, Locale.getDefault())
    private val apiDateTimeFormatter = SimpleDateFormat(API_DATETIME_FORMAT, Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    private val displayDateFormatter = SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.getDefault())
    private val displayDateTimeFormatter =
        SimpleDateFormat(DISPLAY_DATETIME_FORMAT, Locale.getDefault())
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

    fun dateToApiFormat(date: Date): String {
        return apiDateFormatter.format(date)
    }

    fun parseApiDate(apiDateString: String): Date {
        return try {
            apiDateFormatter.parse(apiDateString)
        } catch (e: Exception) {
            Date()
        }
    }

    fun parseDisplayDate(displayDateString: String): Date {
        return try {
            displayDateFormatter.parse(displayDateString)
        } catch (e: Exception) {
            Date()
        }
    }

    fun parseApiDateTime(apiDateTimeString: String): Date {
        return try {
            apiDateTimeFormatter.parse(apiDateTimeString)
        } catch (e: Exception) {
            Date()
        }
    }

    fun formatDateForDisplay(date: Date): String? {
        return try {
            date.let { displayDateFormatter.format(it) }
        } catch (e: Exception) {
            null
        }
    }

    fun formatDateTimeForDisplay(date: Date): String? {
        return try {
            date.let { displayDateTimeFormatter.format(it) }
        } catch (e: Exception) {
            null
        }
    }

    fun formatTimeForDisplay(date: Date): String? {
        return try {
            date.let { displayTimeFormatter.format(it) }
        } catch (e: Exception) {
            null
        }
    }
}