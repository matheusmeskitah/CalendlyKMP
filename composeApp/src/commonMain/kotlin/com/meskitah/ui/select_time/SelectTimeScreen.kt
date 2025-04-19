package com.meskitah.ui.select_time

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import calendlykmp.composeapp.generated.resources.Res
import calendlykmp.composeapp.generated.resources.duration
import calendlykmp.composeapp.generated.resources.select_a_time
import calendlykmp.composeapp.generated.resources.time_zone
import com.meskitah.core.utils.capitalizeFirstChar
import com.meskitah.core.utils.fullDateFormat
import com.meskitah.core.utils.toLong
import com.meskitah.ui.navigation.CalendlyDestination
import com.meskitah.ui.theme.CalendlyTheme
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SelectTimeScreenRoot(
    navController: NavController,
    selectedDate: Long,
    availableTimes: List<String>
) {
    val viewModel: SelectTimeViewModel =
        koinViewModel(parameters = { parametersOf(selectedDate, availableTimes) })
    val state by viewModel.state.collectAsState()

    SelectTimeScreen(
        state = state,
        onBackClick = { navController.navigateUp() },
        onSetTimeZone = { viewModel.handleEvent(SelectTimeEvent.SetTimeZone(it)) },
        onTimeZoneType = { viewModel.handleEvent(SelectTimeEvent.TimeZoneType(it)) },
        onTimeClick = {
            navController.navigate(
                CalendlyDestination.EventDetails(
                    selectedDate = LocalDateTime(
                        date = state.selectedDate,
                        time = LocalTime.parse(it)
                    ).toLong(state.timeZone),
                    selectedTime = it,
                    selectedTimeZoneId = state.timeZone.id
                )
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun SelectTimeScreen(
    modifier: Modifier = Modifier,
    state: SelectTimeState,
    onBackClick: () -> Unit,
    onSetTimeZone: (TimeZone) -> Unit,
    onTimeZoneType: (String) -> Unit,
    onTimeClick: (String) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            LargeTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Column {
                        Text(text = state.selectedDate.dayOfWeek.name.capitalizeFirstChar())
                        Text(text = state.selectedDate.format(fullDateFormat()))
                    }
                }
            )
        }
    ) { innerPadding ->
        var expanded by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    ExposedDropdownMenuBox(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            value = state.timeZoneFilter,
                            onValueChange = onTimeZoneType,
                            label = { Text(text = stringResource(Res.string.time_zone)) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Public,
                                    contentDescription = null
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            isError = !TimeZone.availableZoneIds.contains(state.timeZone.id),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = { focusManager.clearFocus() }
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryEditable)
                        )
                        val filteredOptions = TimeZone.availableZoneIds.filter {
                            it.contains(
                                state.timeZoneFilter,
                                ignoreCase = true
                            )
                        }
                        if (filteredOptions.isNotEmpty()) {
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { }
                            ) {
                                filteredOptions.forEachIndexed { index, id ->
                                    DropdownMenuItem(
                                        text = { Text(text = TimeZone.of(id).toString()) },
                                        onClick = {
                                            expanded = false
                                            focusManager.clearFocus()
                                            onSetTimeZone(TimeZone.of(id))
                                        }
                                    )
                                    if (index < filteredOptions.lastIndex)
                                        HorizontalDivider()
                                }
                            }
                        }
                    }

                    HorizontalDivider(Modifier.padding(top = 32.dp))
                }
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(Res.string.select_a_time),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(vertical = 32.dp)
                    )
                    Text(
                        text = stringResource(Res.string.duration),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                }
            }

            items(key = { it }, items = state.availableTimes) {
                OutlinedButton(
                    onClick = { onTimeClick(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, bottom = 16.dp, end = 24.dp)
                ) {
                    Text(it)
                }
            }
        }
    }
}

@Composable
@Preview
private fun SelectTimeScreenPreview() {
    CalendlyTheme {
        SelectTimeScreen(
            state = SelectTimeState(),
            onBackClick = {},
            onSetTimeZone = {},
            onTimeZoneType = {},
            onTimeClick = {}
        )
    }
}