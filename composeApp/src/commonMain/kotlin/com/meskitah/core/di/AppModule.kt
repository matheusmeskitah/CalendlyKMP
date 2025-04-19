package com.meskitah.core.di

import com.meskitah.data.available_time.repository.AvailableTimeRepositoryImpl
import com.meskitah.data.event_detail.repository.ScheduleEventRepositoryImpl
import com.meskitah.domain.available_time.repository.AvailableTimeRepository
import com.meskitah.domain.event_detail.repository.ScheduleEventRepository
import com.meskitah.ui.available_time.AvailableTimeViewModel
import com.meskitah.ui.event_details.EventDetailsViewModel
import com.meskitah.ui.select_time.SelectTimeViewModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::AvailableTimeRepositoryImpl) { bind<AvailableTimeRepository>() }
    viewModelOf(::AvailableTimeViewModel)
    viewModel { (selectedDate: Long, availableTimes: List<String>) ->
        SelectTimeViewModel(selectedDate, availableTimes)
    }
    singleOf(::ScheduleEventRepositoryImpl) { bind<ScheduleEventRepository>() }
    viewModel { (selectedDate: LocalDate, selectedTime: LocalTime, selectedTimeZone: TimeZone) ->
        EventDetailsViewModel(selectedDate, selectedTime, selectedTimeZone, get())
    }
}