package com.meskitah.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun CalendlyNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = CalendlyDestination.Root
    ) {
        mainNavGraph(navController = navController)
    }
}