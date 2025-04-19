package com.meskitah.data.event_detail.repository

import com.meskitah.domain.event_detail.repository.ScheduleEventRepository
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDateTime

/**
 * Implementation of the [ScheduleEventRepository] interface.  This implementation provides
 * functionality for scheduling events, currently using a mock response for success and failure.
 */
class ScheduleEventRepositoryImpl : ScheduleEventRepository {

    /**
     * Posts an event to be scheduled.
     *
     * This function simulates posting an event with the given details.  Currently, it mocks the
     * process by delaying for 1 second and returning a success message.  In a real implementation,
     * this would likely involve sending the data to a backend service.
     *
     * @param dateTimeUTC The date and time of the event in UTC.
     * @param name The name of the person scheduling the event.
     * @param email The email address of the person scheduling the event.
     * @return A [Result] representing the outcome of the operation.  If successful, it contains
     *         a success message string. If an error occurs, it contains a failure with the exception.
     */
    override suspend fun postEvent(
        dateTimeUTC: LocalDateTime,
        name: String,
        email: String
    ): Result<String> {
        return try {
            // Mock response
            delay(1_000)
            Result.success("Event successfully scheduled! Please check you email")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}