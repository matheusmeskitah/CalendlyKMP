package com.meskitah.ui.available_time

import androidx.compose.material3.SnackbarHostState
import com.meskitah.domain.available_time.model.AvailableTime
import com.meskitah.domain.available_time.model.Data
import kotlinx.datetime.Clock

data class AvailableTimeState(
    val isLoading: Boolean = false,
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
    val availableTime: AvailableTime = AvailableTime(data = Data(availableTimes = emptyList())),
    val initialSelectedDateMillis: Long = Clock.System.now().toEpochMilliseconds(),
    val startDate: String = "",
    val endDate: String = "",
    val showDatePicker: Boolean = false
)
