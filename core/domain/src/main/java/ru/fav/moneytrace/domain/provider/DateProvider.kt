package ru.fav.moneytrace.domain.provider

import java.util.Calendar

interface DateProvider {
    fun getCurrentDate(): Calendar
    fun formatDate(calendar: Calendar): String
    fun formatTime(calendar: Calendar): String
    fun parseDate(dateString: String): Calendar
    fun parseDateTime(dateTimeString: String): Calendar
}
