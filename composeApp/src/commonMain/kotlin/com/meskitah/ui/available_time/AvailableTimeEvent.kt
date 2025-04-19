package com.meskitah.ui.available_time

interface AvailableTimeEvent {
    object CloseDatePicker : AvailableTimeEvent
    object GetAvailableTime : AvailableTimeEvent
    class LaunchSnackBar(val message: String) : AvailableTimeEvent
}