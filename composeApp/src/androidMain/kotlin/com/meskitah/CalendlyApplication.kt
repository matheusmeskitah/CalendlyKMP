package com.meskitah

import android.app.Application
import com.meskitah.core.di.KoinManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CalendlyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CalendlyApplication)
            androidLogger()
            modules(KoinManager.koinModules())
        }
    }
}