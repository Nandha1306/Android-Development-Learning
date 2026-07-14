package com.example.navigationpractice.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigationpractice.screens.DetailsScreen
import com.example.navigationpractice.screens.HomeScreen
import com.example.navigationpractice.screens.ProfileScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.HOME
    ) {

        composable(AppDestinations.HOME) {

            HomeScreen(
                onNavigateToDetails = {
                    navController.navigate(AppDestinations.DETAILS)
                }
            )

        }

        composable(AppDestinations.DETAILS) {

            DetailsScreen(
                onNavigateToProfile = {
                    navController.navigate(AppDestinations.PROFILE)
                }
            )

        }

        composable(AppDestinations.PROFILE) {

            ProfileScreen(
                onBack = {
                    navController.popBackStack()
                }
            )

        }

    }
}