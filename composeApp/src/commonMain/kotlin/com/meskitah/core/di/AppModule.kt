package com.meskitah.core.di

import com.meskitah.data.available_time.repository.AvailableTimeRepositoryImpl
import com.meskitah.domain.available_time.repository.AvailableTimeRepository
import com.meskitah.ui.available_time.AvailableTimeViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::AvailableTimeRepositoryImpl) { bind<AvailableTimeRepository>() }
    viewModelOf(::AvailableTimeViewModel)
}