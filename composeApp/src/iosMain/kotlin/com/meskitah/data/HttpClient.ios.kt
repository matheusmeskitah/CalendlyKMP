package com.meskitah.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual val client: HttpClient
    get() = HttpClient(Darwin) {
        //Timeout plugin to set up timeout milliseconds for client
        install(HttpTimeout) {
            socketTimeoutMillis = 60_000
            requestTimeoutMillis = 60_000
        }

        defaultRequest {
            header("Content-Type", "application/json")
            url("https://5b94bbb0-4b84-4173-8753-c9b46c84fc76.mock.pstmn.io/appointment_availabilities/")
        }

        //ContentNegotiation plugin for negotiation media types between the client and server
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            })
        }
    }