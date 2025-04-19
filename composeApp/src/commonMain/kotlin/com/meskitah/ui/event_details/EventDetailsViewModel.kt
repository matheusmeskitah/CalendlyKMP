package com.meskitah.ui.event_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meskitah.core.utils.convertLocalTimeBetweenTimeZones
import com.meskitah.domain.event_detail.repository.ScheduleEventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone

class EventDetailsViewModel(
    private val selectedDate: LocalDate,
    private val selectedTime: LocalTime,
    private val selectedTimeZone: TimeZone,
    private val repository: ScheduleEventRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EventDetailsState())
    val state: StateFlow<EventDetailsState> = _state

    init {
        _state.update {
            it.copy(
                selectedDate = selectedDate,
                selectedTime = selectedTime,
                selectedTimeZone = selectedTimeZone,
            )
        }
    }

    fun handleEvent(event: EventDetailsEvent) {
        when (event) {
            is EventDetailsEvent.OnNameType -> _state.update {
                it.copy(
                    name = event.text,
                    isNameValid = event.text.isNotBlank()
                )
            }

            is EventDetailsEvent.OnEmailType -> _state.update {
                it.copy(
                    email = event.text,
                    isEmailValid = Regex(
                        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
                    ).matches(event.text)
                )
            }

            EventDetailsEvent.OnScheduleEventClick -> scheduleEvent()
        }
    }

    private fun scheduleEvent() {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            repository.postEvent(
                dateTimeUTC = convertLocalTimeBetweenTimeZones(
                    localTime = _state.value.selectedTime,
                    date = _state.value.selectedDate,
                    fromTimeZone = _state.value.selectedTimeZone,
                    toTimeZone = TimeZone.UTC
                ),
                name = _state.value.name,
                email = _state.value.email
            )
                .onSuccess { message ->
                    _state.update { it.copy(isLoading = false, message = message) }
                }
                .onFailure { exception ->
                    _state.update {
                        it.copy(isLoading = false, message = exception.message ?: "Unknown Error")
                    }
                }
        }
    }
}