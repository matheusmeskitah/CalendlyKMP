package com.meskitah.ui.event_details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import calendlykmp.composeapp.generated.resources.Res
import calendlykmp.composeapp.generated.resources.app_name
import calendlykmp.composeapp.generated.resources.email
import calendlykmp.composeapp.generated.resources.enter_details
import calendlykmp.composeapp.generated.resources.interview_duration
import calendlykmp.composeapp.generated.resources.interview_duration_short
import calendlykmp.composeapp.generated.resources.interview_format_detail
import calendlykmp.composeapp.generated.resources.name
import calendlykmp.composeapp.generated.resources.schedule_event
import calendlykmp.composeapp.generated.resources.terms_of_use_and
import calendlykmp.composeapp.generated.resources.terms_of_use_calendly
import calendlykmp.composeapp.generated.resources.terms_of_use_first
import calendlykmp.composeapp.generated.resources.terms_of_use_privacy
import com.meskitah.core.utils.capitalizeFirstChar
import com.meskitah.core.utils.fullDateFormat
import com.meskitah.core.utils.plus
import com.meskitah.ui.navigation.CalendlyDestination
import com.meskitah.ui.theme.CalendlyTheme
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EventDetailsScreenRoot(
    navController: NavController,
    selectedDate: LocalDate,
    selectedTime: LocalTime,
    selectedTimeZone: TimeZone
) {
    val viewModel: EventDetailsViewModel =
        koinViewModel(parameters = { parametersOf(selectedDate, selectedTime, selectedTimeZone) })
    val state by viewModel.state.collectAsState()

    EventDetailsScreen(
        state = state,
        onBackClick = { navController.navigateUp() },
        onNameType = { viewModel.handleEvent(EventDetailsEvent.OnNameType(it)) },
        onEmailType = { viewModel.handleEvent(EventDetailsEvent.OnEmailType(it)) },
        onScheduleEventClick = { viewModel.handleEvent(EventDetailsEvent.OnScheduleEventClick) },
        goToHome = {
            navController.navigate(CalendlyDestination.AvailableTime(message = state.message)) {
                popUpTo(CalendlyDestination.AvailableTime(message = state.message)) {
                    inclusive = false
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun EventDetailsScreen(
    modifier: Modifier = Modifier,
    state: EventDetailsState,
    onBackClick: () -> Unit,
    onNameType: (String) -> Unit,
    onEmailType: (String) -> Unit,
    onScheduleEventClick: () -> Unit,
    goToHome: () -> Unit
) {
    LaunchedEffect(state.message) {
        if (state.message.isNotBlank())
            goToHome()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = { Text(text = stringResource(Res.string.app_name)) }
            )
        },
        bottomBar = {
            BottomAppBar {
                Button(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    onClick = onScheduleEventClick,
                    enabled = !state.isLoading && state.isNameValid && state.isEmailValid
                ) {
                    if (state.isLoading)
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 1.dp
                        )
                    else Text(stringResource(Res.string.schedule_event))
                }
            }
        }
    ) { innerPadding ->
        val focusManager = LocalFocusManager.current

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.interview_duration),
                style = MaterialTheme.typography.headlineMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
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
                    text = stringResource(Res.string.interview_format_detail),
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "${state.selectedTime} - ${state.selectedTime.plus(DateTimePeriod(minutes = 30))}, " +
                            "${state.selectedDate.dayOfWeek.name.capitalizeFirstChar()}, " +
                            state.selectedDate.format(fullDateFormat()),
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Public,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = state.selectedTimeZone.id,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            HorizontalDivider(Modifier.padding(vertical = 32.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(Res.string.enter_details),
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                value = state.name,
                onValueChange = onNameType,
                label = { Text(stringResource(Res.string.name)) },
                isError = !state.isNameValid,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Words
                )
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 24.dp),
                value = state.email,
                onValueChange = onEmailType,
                label = { Text(stringResource(Res.string.email)) },
                isError = !state.isEmailValid,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )

            Spacer(Modifier.weight(1f))

            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        fontWeight = MaterialTheme.typography.labelSmall.fontWeight
                    )
                ) {
                    append(stringResource(Res.string.terms_of_use_first))
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    append(stringResource(Res.string.terms_of_use_calendly))
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        fontWeight = MaterialTheme.typography.labelSmall.fontWeight
                    )
                ) {
                    append(stringResource(Res.string.terms_of_use_and))
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    append(stringResource(Res.string.terms_of_use_privacy))
                }
            })
        }
    }
}

@Composable
@Preview
private fun EventDetailsScreenPreview() {
    CalendlyTheme {
        EventDetailsScreen(
            state = EventDetailsState(),
            onBackClick = {},
            onNameType = {},
            onEmailType = {},
            onScheduleEventClick = {},
            goToHome = {}
        )
    }
}