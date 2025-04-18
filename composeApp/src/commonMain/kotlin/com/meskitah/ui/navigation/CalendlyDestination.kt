package com.meskitah.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class CalendlyDestination {
    @Serializable
    data object Root: CalendlyDestination()

    @Serializable
    data object AvailableTime: CalendlyDestination()

    @Serializable
    data object SelectTime: CalendlyDestination()

    @Serializable
    data object EventDetail: CalendlyDestination()
}