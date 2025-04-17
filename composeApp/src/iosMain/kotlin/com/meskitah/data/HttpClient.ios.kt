package com.meskitah.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual val client: HttpClient
    get() = HttpClient(Darwin) {

    }