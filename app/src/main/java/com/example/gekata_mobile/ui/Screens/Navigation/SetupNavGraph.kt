package com.example.gekata_mobile.ui.Screens.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gekata_mobile.ModelView.Realisation.ProjectsViewModel
import com.example.gekata_mobile.ui.Screens.PathIndoorScreen.IndoorHomeScreen
import com.example.gekata_mobile.ui.Screens.PathOutdoorScreen.OutdoorHomeScreen
import com.example.gekata_mobile.ui.Screens.PathOutdoorScreen.OutdoorMainScreen
import com.example.gekata_mobile.ui.Screens.PointsScreen.PointsHomeScreen
import com.example.gekata_mobile.ui.Screens.ProjectsListScreen.ProjectsApp


@Composable
fun SetupNavGraph(navController: NavHostController, projectsViewModel: ProjectsViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.Points.route
    )
    {
        composable(route = Screen.Indoor.route) {
            IndoorHomeScreen(projectsViewModel = projectsViewModel, projectsUIStates = projectsViewModel.projectsUIStates)
        }
        composable(route = Screen.Outdoor.route) {
            OutdoorHomeScreen(projectsViewModel = projectsViewModel)
        }
        composable(route = Screen.Points.route) {
            PointsHomeScreen(projectsViewModel = projectsViewModel, projectsUIStates = projectsViewModel.projectsUIStates)
        }
        composable(route = Screen.Projects.route) {
            ProjectsApp(projectsViewModel = projectsViewModel)
        }
    }
}