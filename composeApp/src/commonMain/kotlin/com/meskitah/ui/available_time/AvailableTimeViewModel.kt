package com.meskitah.ui.available_time

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meskitah.core.utils.defaultDateFormat
import com.meskitah.core.utils.getLastDayOfMonth
import com.meskitah.core.utils.requestDateFormat
import com.meskitah.domain.available_time.repository.AvailableTimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.toLocalDateTime

class AvailableTimeViewModel(
    val availableTimeRepository: AvailableTimeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AvailableTimeState())
    val state: StateFlow<AvailableTimeState> = _state

    fun handleEvent(event: AvailableTimeEvent) {
        when (event) {
            AvailableTimeEvent.CloseDatePicker -> _state.update { it.copy(showDatePicker = false) }
            AvailableTimeEvent.GetAvailableTime -> getAvailableTime()
            is AvailableTimeEvent.LaunchSnackBar -> viewModelScope.launch {
                _state.value.snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    private fun getAvailableTime() {
        _state.update { it.copy(isLoading = true) }

        val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val startDate = now.format(defaultDateFormat())
        val endDate = getLastDayOfMonth(year = now.year, month = now.monthNumber)

        viewModelScope.launch {
            availableTimeRepository.getAvailableTimes(
                startDate = startDate,
                endDate = "${endDate}T23:59:59",
                month = now.format(requestDateFormat())
            )
                .onSuccess {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            availableTime = it,
                            showDatePicker = true
                        )
                    }
                }
                .onFailure {
                    _state.update { state -> state.copy(isLoading = false) }
                    _state.value.snackbarHostState.showSnackbar(it.message ?: "Unknown Error")
                }
        }
    }
}