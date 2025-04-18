package com.meskitah.data.available_time.dto

import kotlinx.serialization.Serializable

@Serializable
data class DataDTO(
    val available_times: List<String>
)