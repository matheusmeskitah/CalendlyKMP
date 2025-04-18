package com.meskitah

import androidx.compose.runtime.Composable
import com.meskitah.ui.navigation.CalendlyNavGraph
import com.meskitah.ui.theme.CalendlyTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun CalendlyApp() {
    KoinContext {
        CalendlyTheme {
            CalendlyNavGraph()
        }
    }
}