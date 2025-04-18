package com.meskitah

import androidx.compose.ui.window.ComposeUIViewController
import com.meskitah.core.di.KoinManager

fun MainViewController() = ComposeUIViewController(
    configure = {
        KoinManager.start()
    }) { CalendlyApp() }