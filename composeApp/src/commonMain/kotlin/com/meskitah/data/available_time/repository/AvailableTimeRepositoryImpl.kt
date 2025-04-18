package com.meskitah.data.available_time.repository

import com.meskitah.data.available_time.dto.AvailableTimeDTO
import com.meskitah.data.available_time.mapper.toAvailableTime
import com.meskitah.data.client
import com.meskitah.domain.available_time.model.AvailableTime
import com.meskitah.domain.available_time.repository.AvailableTimeRepository
import io.ktor.client.call.body
import io.ktor.client.request.get

class AvailableTimeRepositoryImpl : AvailableTimeRepository {
    override suspend fun getAvailableTimes(
        startDate: String,
        endDate: String,
        month: String
    ): Result<AvailableTime> {
        return try {
            val response: AvailableTimeDTO? = client.get("available_times") {
                headers.append("x-mock-response-name", month)

                url {
                    parameters.append("start_date_time", startDate)
                    parameters.append("end_date_time", endDate)
                }
            }.body()

            response?.toAvailableTime()?.let {
                if (it.data.availableTimes.isEmpty())
                    Result.failure(Exception("No available dates to book"))
                else Result.success(it)
            } ?: throw Exception("Error retrieving data")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}