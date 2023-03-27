package com.wheelycoolapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wheelycoolapp.feature.input.InputScreen
import com.wheelycoolapp.feature.input.WheelViewModel
import com.wheelycoolapp.feature.wheel.WheelScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    val wheelViewModel: WheelViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.Input.route
    ) {
        composable(Screen.Input.route) {
            InputScreen(
                viewModel = wheelViewModel,
                navigateTo = {
                    navController.navigate(it.route)
                }
            )
        }

        composable(Screen.Wheel.route) {
            WheelScreen(viewModel = wheelViewModel)
        }
    }
}