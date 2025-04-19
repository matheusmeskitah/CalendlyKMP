package com.meskitah.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.meskitah.ui.available_time.AvailableTimeScreenRoot
import com.meskitah.ui.select_time.SelectTimeScreenRoot

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation<CalendlyDestination.Root>(
        startDestination = CalendlyDestination.AvailableTime
    ) {
        composable<CalendlyDestination.AvailableTime> {
            AvailableTimeScreenRoot(navController = navController)
        }

        composable<CalendlyDestination.SelectTime> {
            val selectedDate = it.toRoute<CalendlyDestination.SelectTime>().selectedDate
            val availableTimes = it.toRoute<CalendlyDestination.SelectTime>().availableTimes
            SelectTimeScreenRoot(
                navController = navController,
                selectedDate = selectedDate,
                availableTimes = availableTimes
            )
        }
    }
}