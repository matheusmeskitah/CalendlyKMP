package com.meskitah.ui.available_time

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meskitah.domain.available_time.repository.AvailableTimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AvailableTimeViewModel(
    val availableTimeRepository: AvailableTimeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AvailableTimeState())
    val state: StateFlow<AvailableTimeState> = _state

    init {
        handleEvent(AvailableTimeEvent.GetAvailableTime)
    }

    fun handleEvent(event: AvailableTimeEvent) {
        when (event) {
            AvailableTimeEvent.GetAvailableTime -> getAvailableTime()
        }
    }

    private fun getAvailableTime() {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            availableTimeRepository.getAvailableTimes(
                startDate = "2025-04-01T18:30:00",
                endDate = "2025-04-30T12:29:59"
            )
                .onSuccess {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            availableTime = it
                        )
                    }
                }
                .onFailure {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMsg = it.message ?: "Unknown Error"
                        )
                    }
                }
        }
    }
}