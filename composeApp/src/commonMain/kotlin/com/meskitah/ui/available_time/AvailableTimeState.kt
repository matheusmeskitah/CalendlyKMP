package com.meskitah.ui.available_time

import com.meskitah.domain.available_time.model.AvailableTime
import com.meskitah.domain.available_time.model.Data

data class AvailableTimeState(
    val isLoading: Boolean = true,
    val errorMsg: String = "",
    val availableTime: AvailableTime = AvailableTime(data = Data(availableTimes = emptyList())),
    val startDate: String = "",
    val endDate: String = ""
)
