package com.meskitah.core.utils

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

/**
 * Returns a default [DateTimeFormat] for formatting and parsing [LocalDateTime] instances.
 *
 * The format is "yyyy-MM-ddTHH:mm:ss", where:
 * - yyyy: Year with four digits.
 * - MM: Month number with two digits.
 * - dd: Day of the month with two digits.
 * - T: Separator character.
 * - HH: Hour of the day (24-hour format) with two digits.
 * - mm: Minute of the hour with two digits.
 * - ss: Second of the minute with two digits.
 *
 * Example: "2023-10-27T14:30:00"
 *
 * @return A [DateTimeFormat] object configured for the specified format.
 */
fun defaultDateFormat(): DateTimeFormat<LocalDateTime> {
    return LocalDateTime.Format {
        year(); char('-'); monthNumber(); char('-'); dayOfMonth()
        char('T')
        hour(); char(':'); minute(); char(':'); second()
    }
}

/**
 * Provides a DateTimeFormat for parsing and formatting LocalDateTime instances
 * specifically for request parameters where dates are represented as abbreviated month names
 * followed by the year.
 *
 *  For example: "Jan 2023", "Dec 2024".
 *
 * @return A DateTimeFormat configured to handle the specified date representation.
 */
fun requestDateFormat(): DateTimeFormat<LocalDateTime> {
    return LocalDateTime.Format {
        monthName(MonthNames.ENGLISH_ABBREVIATED); year()
    }
}

/**
 * Creates a [DateTimeFormat] for formatting a [LocalDate] in the full month name, day of month,
 * and year format (e.g., "January 1, 2024").
 *
 * @return A [DateTimeFormat] instance configured to format dates as "Month Day, Year".
 */
fun fullDateFormat(): DateTimeFormat<LocalDate> {
    return LocalDate.Format {
        monthName(MonthNames.ENGLISH_FULL); char(' '); dayOfMonth(); char(','); char(' '); year()
    }
}

/**
 * Returns the last day of the specified month in the given year.
 *
 * @param year The year.
 * @param month The month (1 for January, 12 for December).
 * @return A [LocalDate] representing the last day of the month.
 *
 * @throws IllegalArgumentException if the month is not within the valid range [1..12].
 */
fun getLastDayOfMonth(year: Int, month: Int): LocalDate {
    val firstDayOfNextMonth = if (month == 12)
        LocalDate(year + 1, 1, 1)
    else LocalDate(year, month + 1, 1)

    return firstDayOfNextMonth.minus(DatePeriod(days = 1))
}

/**
 * Converts milliseconds since the Unix epoch to a [LocalDate] in UTC.
 *
 * @param millis The number of milliseconds since the Unix epoch (January 1, 1970, 00:00:00 UTC).
 * @return A [LocalDate] representing the date corresponding to the given milliseconds in UTC.
 *
 * Example:
 * ```kotlin
 * val millis = 1678886400000L // March 15, 2023 00:00:00 UTC
 * val localDate = millisToLocalDate(millis)
 * println(localDate) // Output: 2023-03-15
 * ```
 */
fun millisToLocalDate(millis: Long): LocalDate {
    val instant = Instant.fromEpochMilliseconds(millis)
    val localDateTime = instant.toLocalDateTime(TimeZone.UTC)
    return localDateTime.date
}

/**
 * Converts a Unix timestamp (milliseconds since epoch) to a [LocalDate] in UTC.
 *
 * This function takes a Long representing the number of milliseconds since the Unix epoch (January 1, 1970, 00:00:00 UTC) and
 * converts it to a [LocalDate] representing the corresponding date in the UTC time zone.
 *
 * @receiver Long A Unix timestamp in milliseconds.
 * @return LocalDate The LocalDate corresponding to the timestamp in UTC.
 *
 * @sample
 * ```kotlin
 * val timestamp = 1678886400000L // March 15, 2023, 00:00:00 UTC
 * val localDate = timestamp.toLocalDate() // Returns LocalDate(2023, 3, 15)
 * ```
 */
fun Long.toLocalDate(): LocalDate {
    return Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.UTC).date
}

/**
 * Converts a [LocalTime] and [LocalDate] from one [TimeZone] to another, resulting in a [LocalDateTime] in the target timezone.
 *
 * @param localTime The local time to convert.
 * @param date The local date to combine with the local time.  This is crucial for handling daylight savings correctly.
 * @param fromTimeZone The timezone of the input [localTime] and [date].
 * @param toTimeZone The timezone to convert the time to.
 * @return A [LocalDateTime] representing the equivalent time in the [toTimeZone].
 *
 * @throws IllegalArgumentException if the input values are invalid or cannot be parsed correctly.
 *
 * Example:
 * ```
 * val localTime = LocalTime(10, 0) // 10:00 AM
 * val date = LocalDate(2023, 10, 27) // October 27, 2023
 * val fromTimeZone = TimeZone.of("America/New_York")
 * val toTimeZone = TimeZone.of("Europe/Paris")
 *
 * val convertedDateTime = convertLocalTimeBetweenTimeZones(localTime, date, fromTimeZone, toTimeZone)
 *
 * println(convertedDateTime) // Output: 2023-10-27T14:00
 * ```
 *
 * Notes:
 * - The conversion takes into account daylight savings time (DST) changes that might occur on the given date in either timezone.
 * - Using a [LocalDate] along with the [LocalTime] is essential for accurate conversions, particularly when crossing DST boundaries.  If only the time were used, potential ambiguity or errors could arise depending on the source timezone's DST history.
 * - The function leverages the `kotlinx-datetime` library for time zone handling, ensuring accuracy and consistency across platforms.
 */
fun convertLocalTimeBetweenTimeZones(
    localTime: LocalTime,
    date: LocalDate,
    fromTimeZone: TimeZone,
    toTimeZone: TimeZone
): LocalDateTime {
    val localDateTime = LocalDateTime(date, localTime)

    val instant = localDateTime.toInstant(fromTimeZone)

    val convertedDateTime = instant.toLocalDateTime(toTimeZone)

    return convertedDateTime
}

/**
 * Capitalizes the first character of a string and converts the rest of the string to lowercase.
 *
 * Example usage:
 * ```
 * val myString = "hELLo wORLd"
 * val capitalizedString = myString.capitalizeFirstChar() // capitalizedString will be "Hello world"
 * ```
 *
 * @return The string with the first character capitalized and the rest in lowercase.  Returns an empty string if the input is empty.
 */
fun String.capitalizeFirstChar(): String {
    return this.lowercase().replaceFirstChar { it.uppercase() }
}

/**
 * Converts a LocalDateTime to a Long representing the number of milliseconds since the Unix
 * epoch (January 1, 1970, 00:00:00 UTC) in the specified time zone.
 *
 * @param timeZone The time zone to use for the conversion.
 * @return A Long representing the number of milliseconds since the Unix epoch.
 */
fun LocalDateTime.toLong(timeZone: TimeZone): Long {
    val instant = this.toInstant(timeZone)
    return instant.toEpochMilliseconds()
}

/**
 * Adds a [DateTimePeriod] to this [LocalTime].
 *
 * Since `LocalTime` represents a time of day without any date information, adding a period
 * that includes date-based units (years, months, days) will have no effect on the date part.
 * Only the time components of the period (hours, minutes, seconds, nanoseconds) will be
 * added to the `LocalTime`.
 *
 * For example, adding a period of "1 day, 2 hours" to `10:00 AM` will result in `12:00 PM`.
 *
 * @param period The [DateTimePeriod] to add.  If this period includes date-based units, they will be ignored.
 * @return A [LocalTime] representing the result of adding the [period] to this time.
 */
fun LocalTime.plus(period: DateTimePeriod): LocalTime {
    return LocalDateTime(
        date = LocalDate(year = 1970, month = Month(1), dayOfMonth = 1),
        time = this
    ).toInstant(
        TimeZone.UTC
    ).plus(period = period, timeZone = TimeZone.UTC).toLocalDateTime(TimeZone.UTC).time
}