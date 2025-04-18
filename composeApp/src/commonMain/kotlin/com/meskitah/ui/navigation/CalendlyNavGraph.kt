package com.meskitah.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun CalendlyNavGraph() {
    NavHost(
        navController = rememberNavController(),
        startDestination = CalendlyDestination.Root
    ) {
        mainNavGraph()
    }
}