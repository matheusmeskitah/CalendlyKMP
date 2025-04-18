package com.meskitah.domain.available_time.repository

import com.meskitah.domain.available_time.model.AvailableTime

interface AvailableTimeRepository {
    suspend fun getAvailableTimes(
        startDate: String,
        endDate: String,
        month: String
    ): Result<AvailableTime>
}