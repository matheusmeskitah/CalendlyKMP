package com.meskitah.domain.event_detail.repository

import kotlinx.datetime.LocalDateTime

interface ScheduleEventRepository {
    suspend fun postEvent(
        dateTimeUTC: LocalDateTime,
        name: String,
        email: String
    ): Result<String>
}