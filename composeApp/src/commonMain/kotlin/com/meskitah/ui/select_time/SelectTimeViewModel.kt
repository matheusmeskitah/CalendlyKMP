package com.meskitah.ui.select_time

import androidx.lifecycle.ViewModel
import com.meskitah.core.utils.toLocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

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
                availableTimes = availableTimes
            )
        }
    }

    fun handleEvent(event: SelectTimeEvent) {
        when (event) {
            is SelectTimeEvent.SetTimeZone -> _state.update {
                it.copy(
                    timeZone = event.timeZone,
                    timeZoneFilter = event.timeZone.toString()
                )
            }

            is SelectTimeEvent.TimeZoneType -> _state.update { it.copy(timeZoneFilter = event.text) }
        }
    }
}