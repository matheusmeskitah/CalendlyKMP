package com.meskitah.ui.select_time

import kotlinx.datetime.TimeZone

interface SelectTimeEvent {
    class SetTimeZone(val timeZone: TimeZone): SelectTimeEvent
    class TimeZoneType(val text: String): SelectTimeEvent
}