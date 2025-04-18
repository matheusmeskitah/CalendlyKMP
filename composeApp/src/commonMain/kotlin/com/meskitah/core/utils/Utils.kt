package com.meskitah.core.utils

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

fun defaultDateFormat(): DateTimeFormat<LocalDateTime> {
    return LocalDateTime.Format {
        year(); char('-'); monthNumber(); char('-'); dayOfMonth()
        char('T')
        hour(); char(':'); minute(); char(':'); second()
    }
}

fun requestDateFormat(): DateTimeFormat<LocalDateTime> {
    return LocalDateTime.Format {
        monthName(MonthNames.ENGLISH_ABBREVIATED); year()
    }
}

fun getLastDayOfMonth(year: Int, month: Int): LocalDate {
    val firstDayOfNextMonth = if (month == 12)
        LocalDate(year + 1, 1, 1)
    else LocalDate(year, month + 1, 1)

    return firstDayOfNextMonth.minus(DatePeriod(days = 1))
}

fun millisToLocalDate(millis: Long): LocalDate {
    val instant = Instant.fromEpochMilliseconds(millis)
    val localDateTime = instant.toLocalDateTime(TimeZone.UTC)
    return localDateTime.date
}