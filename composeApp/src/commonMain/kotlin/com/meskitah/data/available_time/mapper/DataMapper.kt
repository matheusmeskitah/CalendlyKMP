package com.meskitah.data.available_time.mapper

import com.meskitah.core.utils.defaultDateFormat
import com.meskitah.data.available_time.dto.DataDTO
import com.meskitah.domain.available_time.model.Data
import kotlinx.datetime.LocalDateTime

fun DataDTO.toData(): Data {
    return Data(
        availableTimes = this.available_times.map {
            LocalDateTime.parse(
                input = it.dropLast(1),
                format = defaultDateFormat()
            )
        }
    )
}