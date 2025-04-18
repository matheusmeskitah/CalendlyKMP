package com.meskitah

import androidx.compose.ui.window.ComposeUIViewController
import com.meskitah.core.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }) { App() }