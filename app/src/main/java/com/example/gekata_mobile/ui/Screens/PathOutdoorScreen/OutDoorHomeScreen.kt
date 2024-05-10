package com.example.gekata_mobile.ui.Screens.PathOutdoorScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gekata_mobile.ModelView.Interfaces.ProjectsUIStates
import com.example.gekata_mobile.ModelView.Realisation.ProjectsViewModel
import com.example.gekata_mobile.ui.Screens.PointsScreen.PointsPathBuildingScreen
import com.example.gekata_mobile.ui.Screens.PointsScreen.PointsSelectionScreen
import com.example.gekata_mobile.ui.Screens.ProjectsListScreen.ErrorScreen
import com.example.gekata_mobile.ui.Screens.ProjectsListScreen.LoadingScreen

@Composable
fun OutdoorHomeScreen(
    projectsViewModel: ProjectsViewModel,
    modifier: Modifier = Modifier
) {
    when (projectsViewModel.projectsUIStates) {
        is ProjectsUIStates.Loading -> LoadingScreen(modifier, "Please, select points and press \"apply\"")
        is ProjectsUIStates.Error -> LoadingScreen(modifier, "Please, select points and press \"apply\"")
        is ProjectsUIStates.Success -> LoadingScreen(modifier, "Please, select points and press \"apply\"")
        is ProjectsUIStates.BuildingPath -> LoadingScreen(modifier, "Please, select points and press \"apply\"")
        is ProjectsUIStates.PathComplete -> OutdoorMainScreen(modifier = modifier, projectsViewModel = projectsViewModel)
    }
}