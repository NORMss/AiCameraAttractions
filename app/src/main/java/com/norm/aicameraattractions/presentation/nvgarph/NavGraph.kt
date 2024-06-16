package com.norm.aicameraattractions.presentation.nvgarph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.norm.aicameraattractions.presentation.navigator.Navigator

@Composable
fun NavGraph(
    startDestination: NewRoute,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        navigation<NewRoute.Navigation>(
            startDestination = NewRoute.NavigatorScreen,
        ) {
            composable<NewRoute.NavigatorScreen> {
                Navigator()
            }
        }
    }
}