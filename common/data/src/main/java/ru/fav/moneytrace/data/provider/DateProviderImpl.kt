package ru.fav.moneytrace.data.provider

import ru.fav.moneytrace.domain.provider.DateProvider
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class DateProviderImpl @Inject constructor() : DateProvider {
    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

    override fun getCurrentDate(): Calendar {
        return Calendar.getInstance().apply {
            timeInMillis = LocalDate.now()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        }
    }

    override fun formatDate(calendar: Calendar): String {
        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault())
            .format(dateFormat)
    }

    override fun formatTime(calendar: Calendar): String {
        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault())
            .format(timeFormat)

    }

    override fun parseDate(dateString: String): Calendar {
        val date = LocalDate.parse(dateString, dateFormat)
        return Calendar.getInstance().apply {
            timeInMillis = date.atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        }
    }

    override fun parseDateTime(dateTimeString: String): Calendar {
        val dateTime = LocalDateTime.parse(dateTimeString, dateTimeFormat)
        return Calendar.getInstance().apply {
            timeInMillis = dateTime.atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        }
    }
}
