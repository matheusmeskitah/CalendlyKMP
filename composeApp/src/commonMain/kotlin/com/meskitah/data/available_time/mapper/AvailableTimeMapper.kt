package com.meskitah.data.available_time.mapper

import com.meskitah.data.available_time.dto.AvailableTimeDTO
import com.meskitah.domain.available_time.model.AvailableTime

fun AvailableTimeDTO.toAvailableTime() : AvailableTime {
    return AvailableTime(
        data = this.data.toData()
    )
}