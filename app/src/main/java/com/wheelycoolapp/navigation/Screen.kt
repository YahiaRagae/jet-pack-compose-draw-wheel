package com.wheelycoolapp.navigation

sealed class Screen(val route: String) {
    // Start
    object Input : Screen("input_screen")
    object Wheel : Screen("wheel_screen")
}