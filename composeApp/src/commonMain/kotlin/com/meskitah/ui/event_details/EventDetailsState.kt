package com.meskitah.ui.event_details

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class EventDetailsState(
    val isLoading: Boolean = false,
    val selectedDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date,
    val selectedTimeZone: TimeZone = TimeZone.UTC,
    val selectedTime: LocalTime = LocalTime.parse("00:00"),
    val name: String = "",
    val email: String = "",
    val isNameValid: Boolean = false,
    val isEmailValid: Boolean = false,
    val message: String = ""
)
