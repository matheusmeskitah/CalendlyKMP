package com.meskitah.ui.select_time

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class SelectTimeState(
    val availableTimes: List<String> = emptyList(),
    val selectedDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date,
    val timeZone: TimeZone = TimeZone.currentSystemDefault(),
    val timeZoneFilter: String = timeZone.toString()
)
