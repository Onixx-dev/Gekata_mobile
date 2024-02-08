package com.example.gekata_mobile.ui.Screens.Navigation

sealed class Screen(val route: String) {
    object Projects: Screen("projects_screen")
    object Points: Screen("points_screen")
    object Outdoor: Screen("path_outdoor_screen")
    object Indoor : Screen("path_indoor_screen")
}