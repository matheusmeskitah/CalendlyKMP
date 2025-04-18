package com.meskitah.ui.available_time

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.meskitah.ui.theme.CalendlyTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AvailableTimeScreenRoot(
    viewModel: AvailableTimeViewModel = koinViewModel<AvailableTimeViewModel>()
) {
    val state by viewModel.state.collectAsState()

    AvailableTimeScreen(state = state)
}

@Composable
private fun AvailableTimeScreen(
    modifier: Modifier = Modifier,
    state: AvailableTimeState
) {
    Scaffold {

    }
}

@Composable
@Preview
private fun AvailableTimeScreenPreview() {
    CalendlyTheme {
        AvailableTimeScreen(
            state = AvailableTimeState()
        )
    }
}