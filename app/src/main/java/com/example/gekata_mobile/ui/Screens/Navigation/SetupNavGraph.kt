package com.example.gekata_mobile.ui.Screens.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gekata_mobile.ui.Screens.PathIndoorScreen.IndoorMainScreen
import com.example.gekata_mobile.ui.Screens.PathOutdoorScreen.OutdoorMainScreen
import com.example.gekata_mobile.ui.Screens.PointsScreen.PointsMainScreen
import com.example.gekata_mobile.ui.Screens.ProjectsListScreen.ProjectsApp


@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Points.route
    )
    {
        composable(route = Screen.Indoor.route) {
            IndoorMainScreen()
        }
        composable(route = Screen.Outdoor.route) {
            OutdoorMainScreen()
        }
        composable(route = Screen.Points.route) {
            PointsMainScreen()
        }
        composable(route = Screen.Projects.route) {
            ProjectsApp()
        }
    }
}