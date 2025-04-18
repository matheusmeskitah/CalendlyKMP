package com.meskitah.core.di

import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

class KoinManager : KoinComponent {
    fun getKoinInstance() = getKoin()

    companion object {
        fun start() {
            startKoin {
                modules(koinModules())
            }
        }
        fun koinModules(): List<Module> {
            return listOf(appModule)
        }
    }
}