package com.meskitah.data.available_time.mapper

import com.meskitah.data.available_time.dto.DataDTO
import com.meskitah.domain.available_time.model.Data

fun DataDTO.toData(): Data {
    return Data(
        availableTimes = this.available_times
    )
}