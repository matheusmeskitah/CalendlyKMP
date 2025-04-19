package com.meskitah.ui.event_details

interface EventDetailsEvent {
    object OnScheduleEventClick : EventDetailsEvent
    class OnNameType(val text: String) : EventDetailsEvent
    class OnEmailType(val text: String) : EventDetailsEvent
}