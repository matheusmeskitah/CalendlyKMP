package com.meskitah.ui.select_time

import androidx.lifecycle.ViewModel
import com.meskitah.core.utils.convertLocalTimeBetweenTimeZones
import com.meskitah.core.utils.toLocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone

class SelectTimeViewModel(
    private val selectedDate: Long,
    private val availableTimes: List<String>
) : ViewModel() {

    private val _state = MutableStateFlow(SelectTimeState())
    val state: StateFlow<SelectTimeState> = _state

    init {
        _state.update {
            it.copy(
                selectedDate = selectedDate.toLocalDate(),
                availableTimes = availableTimes.map {
                    convertLocalTimeBetweenTimeZones(
                        localTime = LocalTime.parse(it),
                        date = selectedDate.toLocalDate(),
                        fromTimeZone = TimeZone.UTC,
                        toTimeZone = _state.value.timeZone
                    ).time.toString()
                }
            )
        }
    }

    fun handleEvent(event: SelectTimeEvent) {
        when (event) {
            is SelectTimeEvent.SetTimeZone -> {
                _state.update {
                    it.copy(
                        availableTimes = _state.value.availableTimes.map {
                            convertLocalTimeBetweenTimeZones(
                                localTime = LocalTime.parse(it),
                                date = _state.value.selectedDate,
                                fromTimeZone = _state.value.timeZone,
                                toTimeZone = event.timeZone
                            ).time.toString()
                        },
                        timeZone = event.timeZone,
                        timeZoneFilter = event.timeZone.toString()
                    )
                }
            }

            is SelectTimeEvent.TimeZoneType -> _state.update { it.copy(timeZoneFilter = event.text) }
        }
    }
}