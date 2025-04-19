package com.meskitah.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class CalendlyDestination {
    @Serializable
    data object Root : CalendlyDestination()

    @Serializable
    data class AvailableTime(val message: String? = null) : CalendlyDestination()

    @Serializable
    data class SelectTime(val selectedDate: Long, val availableTimes: List<String>) :
        CalendlyDestination()

    @Serializable
    data class EventDetails(
        val selectedDate: Long,
        val selectedTime: String,
        val selectedTimeZoneId: String
    ) : CalendlyDestination()
}