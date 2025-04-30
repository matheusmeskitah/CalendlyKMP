package com.meskitah.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.meskitah.core.utils.toLocalDate
import com.meskitah.ui.available_time.AvailableTimeScreenRoot
import com.meskitah.ui.event_details.EventDetailsScreenRoot
import com.meskitah.ui.select_time.SelectTimeScreenRoot
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation<CalendlyDestination.Root>(
        startDestination = CalendlyDestination.AvailableTime::class
    ) {
        composable<CalendlyDestination.AvailableTime> {
            val message = it.toRoute<CalendlyDestination.AvailableTime>().message

            AvailableTimeScreenRoot(navController = navController, message = message)
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

        composable<CalendlyDestination.EventDetails> {
            val selectedDate = it.toRoute<CalendlyDestination.EventDetails>().selectedDate
            val selectedTime = it.toRoute<CalendlyDestination.EventDetails>().selectedTime
            val selectedTimeZoneId =
                it.toRoute<CalendlyDestination.EventDetails>().selectedTimeZoneId

            EventDetailsScreenRoot(
                navController = navController,
                selectedDate = selectedDate.toLocalDate(),
                selectedTime = LocalTime.parse(selectedTime),
                selectedTimeZone = TimeZone.of(zoneId = selectedTimeZoneId)
            )
        }
    }
}