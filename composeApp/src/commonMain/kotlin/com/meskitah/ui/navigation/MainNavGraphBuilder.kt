package com.meskitah.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.meskitah.ui.available_time.AvailableTimeScreenRoot

fun NavGraphBuilder.mainNavGraph() {
    navigation<CalendlyDestination.Root>(
        startDestination = CalendlyDestination.AvailableTime
    ) {
        composable<CalendlyDestination.AvailableTime> {
            AvailableTimeScreenRoot()
        }
    }
}