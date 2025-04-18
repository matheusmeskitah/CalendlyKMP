package com.meskitah.ui.available_time

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import calendlykmp.composeapp.generated.resources.Res
import calendlykmp.composeapp.generated.resources.app_name
import calendlykmp.composeapp.generated.resources.cancel
import calendlykmp.composeapp.generated.resources.interview_duration
import calendlykmp.composeapp.generated.resources.interview_duration_short
import calendlykmp.composeapp.generated.resources.interview_format_detail
import calendlykmp.composeapp.generated.resources.interviewer_name
import calendlykmp.composeapp.generated.resources.ok
import calendlykmp.composeapp.generated.resources.select_a_day
import com.meskitah.core.utils.millisToLocalDate
import com.meskitah.ui.theme.CalendlyTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AvailableTimeScreenRoot(
    viewModel: AvailableTimeViewModel = koinViewModel<AvailableTimeViewModel>()
) {
    val state by viewModel.state.collectAsState()

    AvailableTimeScreen(
        state = state,
        onShowDatePicker = { viewModel.handleEvent(AvailableTimeEvent.GetAvailableTime) },
        onCloseDatePicker = { viewModel.handleEvent(AvailableTimeEvent.CloseDatePicker) })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AvailableTimeScreen(
    modifier: Modifier = Modifier,
    state: AvailableTimeState,
    onShowDatePicker: () -> Unit,
    onCloseDatePicker: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(Res.string.app_name))
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Button(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                    onClick = onShowDatePicker,
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading)
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 1.dp
                        )
                    else Text(text = stringResource(Res.string.select_a_day))
                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = state.snackbarHostState)
        }
    ) { innerPadding ->
        // region Date Picker
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = state.initialSelectedDateMillis,
            selectableDates = object : SelectableDates {
                val today = Clock.System.now().toLocalDateTime(TimeZone.UTC).dayOfYear
                val daysOfTheYear =
                    state.availableTime.data.availableTimes.map { it.dayOfYear }.toSet()
                val years = state.availableTime.data.availableTimes.map { it.year }.toSet()

                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    val currentDay = millisToLocalDate(utcTimeMillis).dayOfYear
                    return daysOfTheYear.contains(currentDay) && currentDay >= today
                }

                override fun isSelectableYear(year: Int): Boolean {
                    return years.contains(year)
                }
            }
        )

        if (state.showDatePicker)
            DatePickerDialog(
                onDismissRequest = onCloseDatePicker,
                confirmButton = {
                    Button(
                        onClick = onCloseDatePicker
                    ) {
                        Text(text = stringResource(Res.string.ok))
                    }
                },
                dismissButton = {
                    Button(
                        onClick = onCloseDatePicker
                    ) {
                        Text(text = stringResource(Res.string.cancel))
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        // endregion

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(60.dp).padding(bottom = 8.dp)
            )
            Text(
                text = stringResource(Res.string.interviewer_name),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = stringResource(Res.string.interview_duration),
                style = MaterialTheme.typography.headlineMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = stringResource(Res.string.interview_duration_short),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Videocam,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    stringResource(Res.string.interview_format_detail),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
@Preview
private fun AvailableTimeScreenPreview() {
    CalendlyTheme {
        AvailableTimeScreen(
            state = AvailableTimeState(),
            onShowDatePicker = {},
            onCloseDatePicker = {}
        )
    }
}